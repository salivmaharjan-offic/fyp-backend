package com.hardware.services.impl;

import com.hardware.dtos.ProductDTO;
import com.hardware.entities.Product;
import com.hardware.exceptions.ResourceNotFoundException;
import com.hardware.mapper.ProductMapper;
import com.hardware.repositories.ProductRepository;
import com.hardware.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;


    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = ProductMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Product not found"));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setCategory(productDTO.getCategory());
        product.setImage(productDTO.getImage());

        return ProductMapper.toDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return ProductMapper.toDTO(product);
    }

    @Override
    public List<ProductDTO> getALlProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }
}
