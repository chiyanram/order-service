package com.amex.order.controller;

import com.amex.order.dto.CreateOrderRequest;
import com.amex.order.dto.OrderSummary;
import com.amex.order.exception.GlobalExceptionHandler;
import com.amex.order.service.OrderService;
import com.google.common.collect.ImmutableList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "/orders")
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
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorInfo.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorInfo.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Technical Problem",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorInfo.class)))
      })
  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OrderSummary> createOrder(
      @RequestBody final CreateOrderRequest createOrderRequest) {

    final OrderSummary orderSummary = orderService.createOrder(createOrderRequest);

    return ResponseEntity.created(URI.create("/orders/" + orderSummary.orderId()))
        .body(orderSummary);
  }

  @Operation(
      summary = "Fetch all orders",
      responses = {
        @ApiResponse(
            description = "orders",
            responseCode = "200",
            content =
                @Content(
                    array = @ArraySchema(schema = @Schema(implementation = OrderSummary.class)))),
        @ApiResponse(
            responseCode = "500",
            description = "Technical Problem",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorInfo.class)))
      })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ImmutableList<OrderSummary> allOrders() {
    return orderService.fetchOrders();
  }

  @Operation(
      summary = "Fetch order by order id",
      responses = {
        @ApiResponse(
            description = "order",
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderSummary.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorInfo.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Technical Problem",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorInfo.class)))
      })
  @GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public OrderSummary orderById(@PathVariable final Long orderId) {
    return orderService.orderById(orderId);
  }
}
