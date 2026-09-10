package com.agvms.sneakerwishlist.repository;

import com.agvms.sneakerwishlist.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
