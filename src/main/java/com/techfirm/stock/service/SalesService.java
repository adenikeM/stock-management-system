package com.techfirm.stock.service;

import com.techfirm.stock.model.Sales;
import com.techfirm.stock.repository.SalesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SalesService {
    private final SalesRepository salesRepository;

    public List<Sales> getAllSale() {
        return salesRepository.findAll();
    }

    public Page<Sales> getAllSale2(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        return salesRepository.findAll(pageable);
    }

    public Optional<Sales> getSaleById(Long id) {
        return salesRepository.findById(Math.toIntExact(id));
    }
//

    public void deleteSale(Long id) {
        salesRepository.findById(Math.toIntExact(id));
    }

    public Sales createSale(Sales sales) {
        log.info("request to create new sales {} ", sales);
        if (!Objects.isNull(sales.getId())) {
            throw new IllegalArgumentException("New sales cannot have id");
        }

        return salesRepository.save(sales);
    }
}



//    public static void sell(Product product, ProductSaleDTO productSaleDTO){
//        product = productService.getProduct(product.getId());
//        if(product.getId() == null){
//            throw new IllegalArgumentException("Product not available");
//            if(product.getAvailableQuantity() >= productSaleDTO.getQuantity()){
//                captureSales(id, name, availableQuantity, price, manufactureDate, expiryDate, size,  productCategory);
//            }
//        }
//    }
//    private static void captureSales(Long id, String name, int availableQuantity, BigDecimal price, LocalDate manufactureDate, LocalDate expiryDate, String size, ProductCategory productCategory) throws IOException {
//        LocalDate Date = LocalDate.now();
//        Product product = new Product(id, name, availableQuantity, price, manufactureDate,
//                expiryDate, size, productCategory);
//        PRODUCTSALES_LIST.add(product);





