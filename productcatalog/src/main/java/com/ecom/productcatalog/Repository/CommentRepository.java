package com.ecom.productcatalog.Repository;

import com.ecom.productcatalog.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByClip(Clip clip);
}