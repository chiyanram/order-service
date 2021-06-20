package com.amex.order.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "orderline")
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderLine {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Builder.Default private OffsetDateTime created = OffsetDateTime.now();

  private OffsetDateTime updated;

  @Version private Long version;

  @JoinColumn private Long orderId;

  @JoinColumn private Long productId;

  private int qty;

  private BigDecimal price;

  private BigDecimal total;
}
