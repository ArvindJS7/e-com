package com.ecom.productcatalog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "clip_likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private AccountType accountType; // USER or SELLER

    @ManyToOne
    @JoinColumn(name = "clip_id")
    @JsonIgnore
    private Clip clip;

    public enum AccountType {
        USER,
        SELLER
    }
}
