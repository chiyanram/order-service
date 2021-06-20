package com.amex.order.service;

import com.amex.order.domain.Product;
import com.amex.order.dto.ProductInfo;
import com.amex.order.exception.NotFoundException;
import com.amex.order.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Log4j2
@AllArgsConstructor
public class DefaultProductService implements ProductService {

  @NonNull private final ProductRepository productRepository;

  @Override
  public ProductInfo findById(@NonNull final Long productId) {

    return productRepository
        .findById(productId)
        .map(toDTO())
        .orElseThrow(
            () -> new NotFoundException(String.format("product not found with id: %s", productId)));
  }

  @Override
  public ProductInfo findByName(@NonNull final String name) {

    return productRepository
        .findByName(name)
        .map(toDTO())
        .orElseThrow(
            () -> new NotFoundException(String.format("product not found with name: %s", name)));
  }

  private Function<Product, ProductInfo> toDTO() {
    return it -> new ProductInfo(it.getId(), it.getName(), it.getPrice());
  }
}
