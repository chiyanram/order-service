package com.amex.order.domain;

import lombok.Value;

import java.util.Optional;

@Value
public class OfferConfig {

  private Optional<Integer> percentage;

  private Optional<MultiSaving> multiSaving;
}
