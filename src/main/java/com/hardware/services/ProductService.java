package com.hardware.services;

import com.hardware.dtos.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
    ProductDTO getProductById(Long id);
    List<ProductDTO> getALlProducts();
}
