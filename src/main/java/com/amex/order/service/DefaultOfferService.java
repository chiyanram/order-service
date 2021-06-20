package com.amex.order.service;

import com.amex.order.domain.Offer;
import com.amex.order.domain.OfferType;
import com.amex.order.domain.OrderLine;
import com.amex.order.dto.DiscountInfo;
import com.amex.order.repository.OfferRepository;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
public class DefaultOfferService implements OfferService {

  private final OfferRepository offerRepository;
  private final ImmutableMap<OfferType, OfferProcessor> processorsByType;

  public DefaultOfferService(
      final OfferRepository offerRepository, final List<OfferProcessor> offerProcessors) {

    this.offerRepository = offerRepository;
    this.processorsByType =
        offerProcessors.stream()
            .collect(ImmutableMap.toImmutableMap(OfferProcessor::type, Function.identity()));
  }

  @Override
  @Transactional(readOnly = true)
  public ImmutableList<DiscountInfo> applyOffers(final ImmutableList<OrderLine> orderLines) {

    final ImmutableMap<Long, OrderLine> linesByProduct =
        orderLines.stream()
            .collect(ImmutableMap.toImmutableMap(OrderLine::getProductId, Function.identity()));

    final List<Offer> offers = offerRepository.currentOffers(OffsetDateTime.now());

    return offers.stream()
        .filter(it -> linesByProduct.containsKey(it.getProductId()))
        .map(
            it ->
                processorsByType
                    .get(it.getType())
                    .process(it, linesByProduct.get(it.getProductId())))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(ImmutableList.toImmutableList());
  }
}
