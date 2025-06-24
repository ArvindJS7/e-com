package com.ecom.productcatalog.Controller;

import com.ecom.productcatalog.model.Product;
import com.ecom.productcatalog.model.Seller;
import com.ecom.productcatalog.Service.SellerService;
import com.ecom.productcatalog.Repository.ProductRepository;
import com.ecom.productcatalog.Repository.SellerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sellers")
@CrossOrigin(origins = "*")
public class SellerController {

    private final SellerService sellerService;
    private final ProductRepository productRepository;

    public SellerController(SellerService sellerService, ProductRepository productRepository) {
        this.sellerService = sellerService;
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Seller> getAllSellers() {
        return sellerService.getAllSellers();
    }

    @GetMapping("/{id}")
    public Seller getSellerById(@PathVariable Long id) {
        return sellerService.getSellerById(id).orElseThrow();
    }

    @GetMapping("/{id}/products")
    public List<Product> getProductsBySeller(@PathVariable Long id) {
        return productRepository.findBySellerId(id);
    }

    @PostMapping("/add")
    public Seller createSeller(@RequestBody Seller seller) {
        return sellerService.createSeller(seller);
    }

    @DeleteMapping("/{id}")
    public void deleteSeller(@PathVariable Long id) {
        sellerService.deleteSeller(id);
    }

    @Autowired
    private SellerRepository sellerRepository;

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
    String name = credentials.get("name");
    String password = credentials.get("password"); 

    Seller seller = sellerRepository.findByNameAndPassword(name, password);
    if (seller != null) {
        return ResponseEntity.ok(seller);
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}


    @PostMapping("/register")
public ResponseEntity<?> registerSeller(@RequestBody Seller seller) {
    Optional<Seller> existing = sellerRepository.findByPhoneNumber(seller.getPhoneNumber());
    if (existing.isPresent()) {
        return ResponseEntity.badRequest().body("Phone number already registered.");
    }
    Seller saved = sellerRepository.save(seller);
    return ResponseEntity.ok(saved);
}


}

