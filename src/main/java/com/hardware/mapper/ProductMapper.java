package com.hardware.mapper;

import com.hardware.dtos.ProductDTO;
import com.hardware.entities.Product;

public class ProductMapper {
    public static ProductDTO toDTO (Product product){
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory(),
                product.getImage()
        );
    }

    public static Product toEntity(ProductDTO productDTO){
        return new Product(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getQuantity(),
                productDTO.getCategory(),
                productDTO.getImage()
        );
    }
}
