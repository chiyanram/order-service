package com.amex.order.dto;

import com.google.common.collect.ImmutableList;

import java.math.BigDecimal;

public record OrderSummary(Long orderId, int qty, BigDecimal total, ImmutableList<OrderLineSummary> orderLines){}
