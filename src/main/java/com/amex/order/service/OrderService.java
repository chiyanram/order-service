package com.amex.order.service;

import com.amex.order.dto.CreateOrderRequest;
import com.amex.order.dto.OrderSummary;
import com.google.common.collect.ImmutableList;

public interface OrderService {

  OrderSummary createOrder(CreateOrderRequest createOrderRequest);

  ImmutableList<OrderSummary> fetchOrders();

  OrderSummary orderById(Long orderId);
}
