// package com.ecom.productcatalog.model;

// import jakarta.persistence.*;
// import lombok.Data;

// @Entity
// @Data
// public class Post {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String caption;

//     private String type; // "image" or "video"

//     private String url;

//     @ManyToOne
//     @JoinColumn(name = "user_id")
//     private User user;
// }
