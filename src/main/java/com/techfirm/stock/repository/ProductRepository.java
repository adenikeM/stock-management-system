package com.techfirm.stock.repository;

import com.techfirm.stock.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByIdIn(@NonNull List<Long> ids);
    @Query("select p from Product p where UPPER(p.name) LIKE CONCAT('%',UPPER(?1),'%') and UPPER( p.colour) LIKE CONCAT( '%',UPPER(?2),'%') and UPPER( p.size) LIKE  CONCAT('%',UPPER(?3),'%')")
    Page<Product> findByNameContainingOrColourContainingOrSizeContaining(String name, String colour, String size, Pageable pageable);


//   @Modifying
//   @Query("update Product p set p.availableQuantity=(p.availableQuantity + :availableQuantity) WHERE p.id=:id")
//    Product updateAvailableQuantityById(Integer availableQuantity,Long id);
}
