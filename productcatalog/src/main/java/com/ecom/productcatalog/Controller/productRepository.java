package com.ecom.productcatalog.Controller;


import java.util.List;
import java.util.Optional;

import com.ecom.productcatalog.model.Product;


public interface productRepository {

    static Optional<Product> findById(Long id) {
    
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    static void deleteById(Long id) {
        
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    static boolean existsById(Long id) {
        
        throw new UnsupportedOperationException("Unimplemented method 'existsById'");
    }

     List<Product> findBySellerId(Long sellerId);
}
