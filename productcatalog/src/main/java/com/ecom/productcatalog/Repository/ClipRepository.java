package com.ecom.productcatalog.Repository;

import com.ecom.productcatalog.model.Clip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClipRepository extends JpaRepository<Clip, Long> {
}