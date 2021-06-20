package com.amex.order.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "product")
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Builder.Default private OffsetDateTime created = OffsetDateTime.now();

  private OffsetDateTime updated;

  @Version private Long version;

  private String name;

  private BigDecimal price;
}
