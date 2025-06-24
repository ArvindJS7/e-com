// package com.ecom.productcatalog.Service;

// import java.util.List;

// import org.springframework.stereotype.Service;

// import com.ecom.productcatalog.Repository.CategoryRepository;
// import com.ecom.productcatalog.model.Category;

// @Service
// public class CategoryService {

//     private final CategoryRepository categoryRepository;

//     public CategoryService(CategoryRepository categoryRepository){
//         this.categoryRepository = categoryRepository;
//     }

//     public List<Category> getAllCategories() {
//         return categoryRepository.findAll();
//     }

//     // public ResponseEntity<List<Category>> addCategory(Category category) {
//     //     Category saved = categoryRepository.save(category);
//     //     return new ResponseEntity<>(saved, HttpStatus.CREATED);
//     // }
//     public Category addCategory(Category category) {
//         return categoryRepository.save(category);
//     }

// }
