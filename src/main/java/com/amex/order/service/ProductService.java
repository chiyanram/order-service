package com.amex.order.service;

import com.amex.order.dto.ProductInfo;

public interface ProductService {

  ProductInfo findById(Long productId);

  ProductInfo findByName(String name);
}
