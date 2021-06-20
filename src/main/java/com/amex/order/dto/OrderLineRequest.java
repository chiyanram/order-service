package com.amex.order.dto;

import com.amex.order.exception.BadRequestException;

import java.util.Objects;

public record OrderLineRequest(Long productId, int qty) {
  public OrderLineRequest {
    if(Objects.isNull(productId)){
      throw new BadRequestException("product id must be present in order request.");
    }
  }
}
