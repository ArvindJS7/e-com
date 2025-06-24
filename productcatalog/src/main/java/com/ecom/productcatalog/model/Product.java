package com.ecom.productcatalog.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;
    @Column(name = "image_path")
    private String imagePath;
    private Boolean available = true;


    //private String imagePath; // For storing uploaded image filename or URL


@ManyToOne(fetch = FetchType.EAGER, optional = false)
@JoinColumn(name = "seller_id", referencedColumnName = "id")
private Seller seller;

}

