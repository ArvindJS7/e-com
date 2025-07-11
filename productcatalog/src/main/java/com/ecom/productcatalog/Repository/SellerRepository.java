package com.ecom.productcatalog.Repository;

import com.ecom.productcatalog.model.Seller;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
   Seller findByNameAndPassword(String name, String password);

  
    Optional<Seller> findByPhoneNumber(String phoneNumber);


    Optional<Seller> findByPhoneNumberAndPassword(String phone, String password);

}
