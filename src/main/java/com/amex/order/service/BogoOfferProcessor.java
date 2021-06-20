package com.amex.order.service;

import com.amex.order.domain.Offer;
import com.amex.order.domain.OfferType;
import com.amex.order.domain.OrderLine;
import com.amex.order.dto.DiscountInfo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class BogoOfferProcessor implements OfferProcessor {

  @Override
  public Optional<DiscountInfo> process(final Offer offer, final OrderLine orderLine) {
    final int discountItem = orderLine.getQty() / 2;
    if (discountItem > 0) {
      final BigDecimal discountPrice =
          orderLine.getPrice().multiply(BigDecimal.valueOf(discountItem));
      return Optional.of(new DiscountInfo(discountPrice));
    }
    return Optional.empty();
  }

  @Override
  public OfferType type() {
    return OfferType.BOGO;
  }
}
