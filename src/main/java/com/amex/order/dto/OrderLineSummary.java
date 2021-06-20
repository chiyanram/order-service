package com.amex.order.dto;

import java.math.BigDecimal;

public record OrderLineSummary(Long lineNumber, String name, int qty, BigDecimal price) {}
