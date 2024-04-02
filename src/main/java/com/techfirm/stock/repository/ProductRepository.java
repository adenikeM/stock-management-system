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

    String KEYWORD = "select p from Product p where UPPER(p.name) LIKE CONCAT('%',UPPER(?1),'%') and UPPER( p.colour) LIKE CONCAT( '%',UPPER(?2),'%') and UPPER( p.size) LIKE  CONCAT('%',UPPER(?3),'%')";
    @Query(KEYWORD)
    Page<Product> findByNameAndColourAndSize(String name, String colour, String size, Pageable pageable);
}
