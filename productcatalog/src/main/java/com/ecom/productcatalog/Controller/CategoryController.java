// package com.ecom.productcatalog.Controller;

// import java.util.List;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.ecom.productcatalog.Service.CategoryService;
// import com.ecom.productcatalog.model.Category;

// @RestController
// @RequestMapping("/api/categories")
// @CrossOrigin(origins = "http://localhost:5173")
// public class CategoryController {

//     private final CategoryService categoryService;

//     public CategoryController (CategoryService categoryService){
//         this.categoryService = categoryService;
//     }

//     @GetMapping
//     public List<Category> getAllCategories(){
//         return categoryService.getAllCategories();
//     }

//     // @PostMapping
//     // public List<Category> addCategory(@RequestBody Category category) {
//     //     return categoryService.addCategory(category);
        
//     // }
//     @PostMapping("/add")
//     public ResponseEntity<Category> addCategory(@RequestBody Category category) {
//         Category saved = categoryService.addCategory(category);
//         return new ResponseEntity<>(saved, HttpStatus.CREATED);
//     }


// }
