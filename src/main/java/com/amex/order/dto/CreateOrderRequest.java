package com.amex.order.dto;

import com.amex.order.exception.BadRequestException;
import com.google.common.collect.ImmutableList;

public record CreateOrderRequest(ImmutableList<OrderLineRequest> items){

  public CreateOrderRequest{
    if(items.isEmpty()){
      throw new BadRequestException("items should be present in order request.");
    }
  }
}
