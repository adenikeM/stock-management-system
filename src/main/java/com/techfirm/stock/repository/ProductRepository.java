package com.techfirm.stock.repository;

import com.techfirm.stock.model.Product;
import com.techfirm.stock.model.ProductCategory;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByIdIn(@NonNull List<Long> ids);
    @Query
    Page<Product> findByNameAndColour(String name, String colour,  Pageable pageable);
}
