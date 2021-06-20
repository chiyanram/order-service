package com.amex.order.dto;

import java.math.BigDecimal;

public record ProductInfo(Long productId, String name, BigDecimal price) { }
