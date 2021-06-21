package com.amex.order.controller

import com.amex.order.AbstractIntegrationSpec
import com.amex.order.domain.Order
import com.amex.order.domain.OrderLine
import com.amex.order.dto.CreateOrderRequest
import com.amex.order.dto.OrderLineRequest
import com.amex.order.dto.OrderSummary
import com.amex.order.exception.GlobalExceptionHandler
import com.amex.order.repository.OrderLineRepository
import com.amex.order.repository.OrderRepository
import com.amex.order.service.ProductService
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableList
import io.restassured.http.ContentType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

import static io.restassured.RestAssured.given

class OrderControllerIT extends AbstractIntegrationSpec {

    @Autowired
    private ObjectMapper objectMapper

    @Autowired
    private ProductService productService

    @Autowired
    private OrderLineRepository lineRepository;

    @Autowired
    private OrderRepository orderRepository;

   
    def cleanup() {
        lineRepository.deleteAll()
        orderRepository.deleteAll()
    }

    def "order with single item"() {
        given:
        def productDTO = productService.findByName("Apple")
        def items = ImmutableList.of(new OrderLineRequest(productDTO.productId(), 2))
        def orderRequest = new CreateOrderRequest(items)
        def createOrderRequest = objectMapper.writeValueAsString(orderRequest)

        when:
        def response = given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .when()
            .body(createOrderRequest)
            .post("/orders")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .contentType(ContentType.JSON)
            .extract()
            .response()
            .asString()

        then:
        def value = objectMapper.readValue(response, OrderSummary.class)
        value
        value.qty() == 2
        value.total() == BigDecimal.valueOf(0.60)
        value.orderLines().size() == 1
        with(value.orderLines()[0]) {
            name() == "Apple"
            qty() == 2
            price() == 0.60
            total() == 1.20
        }
    }

    def "order with multiple item"() {
        given:
        def apple = productService.findByName("Apple")
        def orange = productService.findByName("Orange")
        def items = ImmutableList.of(new OrderLineRequest(apple.productId(), 2), new OrderLineRequest(orange.productId(), 3))
        def orderRequest = new CreateOrderRequest(items)
        def createOrderRequest = objectMapper.writeValueAsString(orderRequest)

        when:
        def response = given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .when()
            .body(createOrderRequest)
            .post("/orders")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .contentType(ContentType.JSON)
            .extract()
            .response()
            .asString()

        then:
        def value = objectMapper.readValue(response, OrderSummary.class)
        value
        value.qty() == 5
        value.total() == BigDecimal.valueOf(1.10)
        value.orderLines().size() == 2
        with(value.orderLines()[0]) {
            name() == "Apple"
            qty() == 2
            price() == 0.60
            total() == 1.20
        }

        with(value.orderLines()[1]) {
            name() == "Orange"
            qty() == 3
            price() == 0.25
            total() == 0.75
        }
    }

    def "create order with invalid product"() {
        given:
        def items = ImmutableList.of(new OrderLineRequest(-1, 2))
        def orderRequest = new CreateOrderRequest(items)
        def createOrderRequest = objectMapper.writeValueAsString(orderRequest)

        when:
        def response = given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .when()
            .body(createOrderRequest)
            .post("/orders")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .contentType(ContentType.JSON)
            .extract()
            .response()
            .asString()

        then:
        def errorInfo = objectMapper.readValue(response, GlobalExceptionHandler.ErrorInfo.class)
        errorInfo
        with(errorInfo) {
            message() == "product not found with id: -1"
            path() == "/orders"
            status() == 404
        }
    }

    def "fetch all orders"() {
        given:
        def productDTO = productService.findByName("Apple")
        def order = orderRepository.save(new Order(qty: 2, total: 2.40))
        def orderLine = lineRepository.save(new OrderLine(orderId: order.id, productId: productDTO.productId(), qty: 2, price: 1.20, total: 2.40))

        when:
        def response = given()
            .accept(ContentType.JSON)
            .when()
            .get("/orders")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .response()
            .asString()

        then:
        def orders = objectMapper.readValue(response, new TypeReference<ImmutableList<OrderSummary>>() {})
        orders
        orders.size() == 1
        with(orders[0]) {
            qty() == 2
            total() == 2.40
        }

        orders[0].orderLines().size() == 1
        with(orders[0].orderLines()[0]) {
            qty() == 2
            total() == 2.40
            name() == "Apple"
        }
    }
}