package com.amex.order.repository;

import com.amex.order.domain.OrderLine;
import com.amex.order.domain.OrderLineAndProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@SuppressWarnings("InterfaceNeverImplemented")
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

  @Query(
      "select ol.id as id, ol.qty as qty, ol.price as price, ol.total as total, p.name as name "
          + "from OrderLine ol left join Product p on ol.productId = p.id where ol.orderId = :orderId")
  List<OrderLineAndProduct> findByOrderId(Long orderId);
}
