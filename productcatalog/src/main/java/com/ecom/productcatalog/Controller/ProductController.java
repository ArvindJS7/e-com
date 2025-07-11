package com.ecom.productcatalog.Controller;

import com.ecom.productcatalog.Repository.ProductRepository;
import com.ecom.productcatalog.Repository.SellerRepository;
import com.ecom.productcatalog.Service.ProductService;
import com.ecom.productcatalog.model.Product;
import com.ecom.productcatalog.model.Seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Handle JSON parsing error
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJsonParseError(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
        return ResponseEntity.badRequest().body("❌ JSON error: " + ex.getMessage());
    }

    // Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get products by seller
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Product>> getProductsBySeller(@PathVariable Long sellerId) {
        return ResponseEntity.ok(productRepository.findBySellerId(sellerId));
    }

    // Add product
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(
            @RequestParam("sellerId") Long sellerId,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("image") MultipartFile imageFile
    ) {
        try {
            Optional<Seller> sellerOpt = sellerRepository.findById(sellerId);
            if (!sellerOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Invalid seller ID");
            }

            // Save image to uploads/
            String uploadDir = "uploads/";
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) uploadFolder.mkdirs();

            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setSeller(sellerOpt.get());
            product.setImagePath(filePath.toString()); // ✅ Save path as string

            Product savedProduct = productRepository.save(product);
            return ResponseEntity.ok(savedProduct);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving product: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Map<String, Object> data) {
    Optional<Product> productOpt = productRepository.findById(id);
    if (!productOpt.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    Product product = productOpt.get();

    product.setName(data.get("name").toString());
    product.setDescription(data.get("description").toString());
    product.setPrice(Double.parseDouble(data.get("price").toString()));

    // ✅ Only update image path if it's in the payload (optional)
    if (data.containsKey("imagePath")) {
        String imagePath = data.get("imagePath").toString();
        if (imagePath != null && !imagePath.trim().isEmpty()) {
            product.setImagePath(imagePath);
        }
    }

    productRepository.save(product);
    return ResponseEntity.ok(product);
}


    // Delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Toggle availability
    @PutMapping("/{id}/toggle-availability")
    public ResponseEntity<String> toggleAvailability(@PathVariable Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setAvailable(!product.getAvailable());
            productRepository.save(product);
            return ResponseEntity.ok("Availability toggled");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Serve product image
    @GetMapping("/product-image/{id}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            String imagePath = productOpt.get().getImagePath();
            if (imagePath == null || imagePath.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            try {
                Path path = Paths.get(imagePath.replace("\\", File.separator));
                byte[] imageBytes = Files.readAllBytes(path);

                MediaType mediaType = getImageMediaType(path);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(mediaType);

                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);

            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    private MediaType getImageMediaType(Path path) {
        try {
            String mimeType = Files.probeContentType(path);
            return mimeType != null ? MediaType.parseMediaType(mimeType) : MediaType.IMAGE_JPEG;
        } catch (IOException e) {
            return MediaType.IMAGE_JPEG;
        }
    }
}
