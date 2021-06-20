package com.amex.order.service;

import com.amex.order.dto.CreateOrderRequest;
import com.amex.order.dto.OrderSummary;

public interface OrderService {

  OrderSummary createOrder(CreateOrderRequest createOrderRequest);
}
