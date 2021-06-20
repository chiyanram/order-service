package com.amex.order.repository;

import com.amex.order.domain.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

  @Query("select o from Offer o where o.startDate <= :period and o.endDate >= :period")
  List<Offer> currentOffers(OffsetDateTime period);
}
