package com.amex.order.service;

import com.amex.order.domain.Offer;
import com.amex.order.domain.OfferType;
import com.amex.order.domain.OrderLine;
import com.amex.order.dto.DiscountInfo;

import java.util.Optional;

public interface OfferProcessor {

  Optional<DiscountInfo> process(Offer offer, OrderLine orderLine);

  OfferType type();
}
