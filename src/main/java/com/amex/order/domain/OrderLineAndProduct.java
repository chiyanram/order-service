package com.amex.order.domain;

import java.math.BigDecimal;

public interface OrderLineAndProduct {

  Long getId();

  int getQty();

  BigDecimal getPrice();

  BigDecimal getTotal();

  String getName();
}
