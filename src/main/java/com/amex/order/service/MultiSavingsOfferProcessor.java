package com.amex.order.service;

import com.amex.order.domain.*;
import com.amex.order.dto.DiscountInfo;
import com.amex.order.exception.ServiceExecutionException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@Log4j2
public class MultiSavingsOfferProcessor implements OfferProcessor {

  @Override
  public Optional<DiscountInfo> process(final Offer offer, final OrderLine orderLine) {

    final OfferConfig config = offer.getConfig();
    final MultiSaving multiSaving =
        config
            .getMultiSaving()
            .orElseThrow(
                () -> new ServiceExecutionException("multi saving config must be present"));

    if (orderLine.getQty() >= multiSaving.getBuy()) {

      final int div = orderLine.getQty() / multiSaving.getBuy();
      final int diff = multiSaving.getBuy() - multiSaving.getPrice();

      final BigDecimal price =
          orderLine.getPrice().multiply(BigDecimal.valueOf(div).multiply(BigDecimal.valueOf(diff)));

      return Optional.of(new DiscountInfo(price));
    }

    return Optional.empty();
  }

  @Override
  public OfferType type() {
    return OfferType.MULTI_SAVINGS;
  }
}
