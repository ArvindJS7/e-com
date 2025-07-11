package com.ecom.productcatalog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private AccountType accountType; // USER or SELLER

    private String text;

    @ManyToOne
    @JoinColumn(name = "clip_id")
    @JsonBackReference
    private Clip clip;

    public enum AccountType {
        USER,
        SELLER
    }
}
