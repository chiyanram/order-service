package com.amex.order.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Order order = (Order) o;

    return Objects.equals(getId(), order.getId());
  }

  @Override
  public int hashCode() {
    return 737800560;
  }
}
