package com.amex.order.service;

import com.amex.order.domain.OrderLine;
import com.amex.order.dto.DiscountInfo;
import com.google.common.collect.ImmutableList;

public interface OfferService {

  ImmutableList<DiscountInfo> applyOffers(final ImmutableList<OrderLine> orderLines);
}
