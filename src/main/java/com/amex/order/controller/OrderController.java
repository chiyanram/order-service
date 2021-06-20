package com.amex.order.controller;

import com.amex.order.dto.CreateOrderRequest;
import com.amex.order.dto.OrderSummary;
import com.amex.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(
    value = "/orders",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @Operation(
      summary = "create order",
      responses = {
        @ApiResponse(
            description = "Order Created",
            responseCode = "201",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderSummary.class))),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Technical Problem",
            content = @Content(schema = @Schema(hidden = true)))
      })
  @PostMapping
  public ResponseEntity<OrderSummary> createOrder(
      @RequestBody final CreateOrderRequest createOrderRequest) {

    val orderSummary = orderService.createOrder(createOrderRequest);

    return ResponseEntity.created(URI.create("/orders/" + orderSummary.orderId()))
        .body(orderSummary);
  }
}
