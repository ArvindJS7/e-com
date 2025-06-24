package com.ecom.productcatalog.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecom.productcatalog.Repository.ProductRepository;
import com.ecom.productcatalog.model.Product;

@Service
public class ProductService {

     private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // public List<Product> getProductByCategory(Long categoryId) {
    //     return productRepository.findByCategoryId(categoryId);
    // }

    public String addProduct(Product product) {
         productRepository.save(product);
         return "success";
    }

    // public Product getProductById(Long id) {
    //     return productRepository.findById(id);
        
    // }

    public Product getProductById(Long id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
}



}
