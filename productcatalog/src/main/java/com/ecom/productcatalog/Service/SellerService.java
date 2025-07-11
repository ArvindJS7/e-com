package com.ecom.productcatalog.Service;

import com.ecom.productcatalog.model.Seller;
import com.ecom.productcatalog.Repository.SellerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;

    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    // Used in ProductController or admin
    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    // ✅ Used in AuthController to get profile picture or by ID
    public Optional<Seller> getSellerById(Long id) {
        return sellerRepository.findById(id);
    }

    // ✅ Used in AuthController for registration
    public Seller saveSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    // ✅ Used in AuthController for login
    public Optional<Seller> findByPhoneAndPassword(String phone, String password) {
        return sellerRepository.findByPhoneNumberAndPassword(phone, password);
    }

    // ✅ Used in AuthController for duplicate check during registration
    public Optional<Seller> findByPhone(String phone) {
        return sellerRepository.findByPhoneNumber(phone);
    }

    // Used for admin purposes
    public void deleteSeller(Long id) {
        sellerRepository.deleteById(id);
    }

    public Seller createSeller(Seller seller) {
        return saveSeller(seller);
    }
}
