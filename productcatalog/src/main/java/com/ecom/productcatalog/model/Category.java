// package com.ecom.productcatalog.model;

// import java.util.List;


// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// import com.fasterxml.jackson.annotation.JsonManagedReference;

// import jakarta.persistence.CascadeType;
// import jakarta.persistence.Entity;

// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.OneToMany;
// import lombok.Data;


// @Data
// @Entity
// @JsonIgnoreProperties

// public class Category {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
//     private String name;

//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
// @JsonManagedReference
//     private List<Product> products ;

// }

