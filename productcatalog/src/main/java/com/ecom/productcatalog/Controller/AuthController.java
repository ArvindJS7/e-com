package com.ecom.productcatalog.Controller;

import com.ecom.productcatalog.Service.SellerService;
import com.ecom.productcatalog.model.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.nio.file.*;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private SellerService sellerService;

    // ✅ Login using phone & password
    @PostMapping("/login")
    public ResponseEntity<Seller> login(@RequestBody Map<String, String> data) {
        String phone = data.get("phoneNumber");
        String password = data.get("password");

        return sellerService.findByPhoneAndPassword(phone, password)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
    }

    // ✅ Register using JSON (remove multipart version)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Seller seller) {
        if (sellerService.findByPhone(seller.getPhoneNumber()).isPresent()) {
            return ResponseEntity.badRequest().body("Phone number already registered.");
        }

        Seller savedSeller = sellerService.createSeller(seller);
        return ResponseEntity.ok(savedSeller);
    }

    // ✅ Serve profile image
    @GetMapping("/profile-picture/{sellerId}")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable Long sellerId) {
        Optional<Seller> seller = sellerService.getSellerById(sellerId);
        if (seller.isPresent() && seller.get().getProfilePicturePath() != null) {
            try {
                Path path = Paths.get(seller.get().getProfilePicturePath());
                byte[] image = Files.readAllBytes(path);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                return new ResponseEntity<>(image, headers, HttpStatus.OK);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}
