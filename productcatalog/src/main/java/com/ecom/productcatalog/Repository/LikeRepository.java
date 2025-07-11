package com.ecom.productcatalog.Repository;

import com.ecom.productcatalog.model.*;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserIdAndAccountTypeAndClip(Long userId, Like.AccountType accountType, Clip clip);
}