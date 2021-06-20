package com.amex.order.service;

import com.amex.order.domain.Order;
import com.amex.order.domain.OrderLine;
import com.amex.order.dto.*;
import com.amex.order.repository.OrderLineRepository;
import com.amex.order.repository.OrderRepository;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class DefaultOrderService implements OrderService {

  @NonNull private final ProductService productService;
  @NonNull private final OrderRepository orderRepository;
  @NonNull private final OrderLineRepository orderLineRepository;

  @Override
  public OrderSummary createOrder(@NonNull final CreateOrderRequest createOrderRequest) {

    final ImmutableMap<Long, ProductInfo> productsById =
        createOrderRequest.items().stream()
            .map(it -> productService.findById(it.productId()))
            .collect(ImmutableMap.toImmutableMap(ProductInfo::productId, Function.identity()));

    final ImmutableList<OrderLine> orderLines =
        createOrderRequest.items().stream()
            .map(it -> createOrderLine(it, productsById.get(it.productId())))
            .collect(ImmutableList.toImmutableList());

    final int totalItems = orderLines.stream().mapToInt(OrderLine::getQty).sum();

    final BigDecimal orderTotal =
        orderLines.stream()
            .map(OrderLine::getPrice)
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);

    final Order order =
        orderRepository.save(Order.builder().qty(totalItems).total(orderTotal).build());

    final ImmutableList<OrderLine> linesWithOrderId =
        orderLines.stream()
            .map(line -> line.toBuilder().orderId(order.getId()).build())
            .collect(ImmutableList.toImmutableList());

    final ImmutableList<OrderLineSummary> orderLineSummaries =
        orderLineRepository.saveAll(linesWithOrderId).stream()
            .map(it -> buildOrderLineSummary(productsById, it))
            .collect(ImmutableList.toImmutableList());

    return new OrderSummary(order.getId(), totalItems, orderTotal, orderLineSummaries);
  }

  private OrderLineSummary buildOrderLineSummary(
      final ImmutableMap<Long, ProductInfo> productsById, final OrderLine it) {

    return new OrderLineSummary(
        it.getId(), productsById.get(it.getProductId()).name(), it.getQty(), it.getPrice());
  }

  private OrderLine createOrderLine(
      final OrderLineRequest orderLineRequest, final ProductInfo productInfo) {

    return OrderLine.builder()
        .productId(productInfo.productId())
        .qty(orderLineRequest.qty())
        .price(productInfo.price().multiply(BigDecimal.valueOf(orderLineRequest.qty())))
        .build();
  }
}
