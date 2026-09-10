package com.agvms.sneakerwishlist.repository;

import com.agvms.sneakerwishlist.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
