package com.amex.order.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Table(name = "orders")
@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Builder.Default private OffsetDateTime created = OffsetDateTime.now();

  private OffsetDateTime updated;

  @Version private Long version;

  private int qty;

  private BigDecimal total;
}
