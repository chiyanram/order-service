package com.amex.order.repository;

import com.amex.order.domain.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("InterfaceNeverImplemented")
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {}
