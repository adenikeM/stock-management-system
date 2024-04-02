package com.techfirm.stock.repository;

import com.techfirm.stock.model.Location;
import com.techfirm.stock.model.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    String KEYWORD = "select c from ProductCategory c where UPPER(c.categoryName) LIKE CONCAT('%',UPPER(?1),'%') and UPPER( c.location) LIKE CONCAT( '%',UPPER(?2),'%')";
    @Query(KEYWORD)
    Page<ProductCategory> findByCategoryNameAndLocationContainsIgnoreCase(String categoryName, Location location, Pageable pageable);
}
