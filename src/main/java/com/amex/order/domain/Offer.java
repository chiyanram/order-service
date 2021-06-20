package com.amex.order.domain;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Table(name = "offer")
@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class)})
public class Offer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Builder.Default private OffsetDateTime created = OffsetDateTime.now();

  private OffsetDateTime updated;

  @Version private Long version;

  private OffsetDateTime startDate;

  private OffsetDateTime endDate;

  @JoinColumn private Long productId;

  @Enumerated private OfferType type;

  @Type(type = "json")
  private OfferConfig config;
}
