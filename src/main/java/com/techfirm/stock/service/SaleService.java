package com.techfirm.stock.service;

import com.techfirm.stock.model.Product;
import com.techfirm.stock.model.ProductCategory;
import com.techfirm.stock.model.ProductSaleDTO;
import com.techfirm.stock.model.Sale;
import com.techfirm.stock.model.dto.ProductDTO;
import com.techfirm.stock.repository.SaleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductService productService;

    public SaleService(SaleRepository saleRepository, ProductService productService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
    }

    public List<Sale> getAllSale() {
        return saleRepository.findAll();
    }

    public Optional<Sale> getSaleById(Long id) {
        return saleRepository.findById(Math.toIntExact(id));
    }
//    public Sale createSale(Sale sale){
//        saleRepository.save(sale);
//    }
//    public Sale updateSale(Sale sale, ) {
//        Long SaleId = sale.getId();
//        if (SaleId == null) {
//            throw new IllegalArgumentException("Sale id cannot be null");}
//        Product retrievedProduct = productService.getProduct()
//        if (sale.getQuantitySold() < retrievedProduct.getAvailableQuantity()) {
//                throw new IllegalArgumentException("No enough product quantity");
//            }
//           Integer Quantity = retrievedProduct.getAvailableQuantity() - sale.getQuantitySold();
//        return saleRepository.save(sale);
//    }

    public void deleteSale(Long id) {
        saleRepository.findById(Math.toIntExact(id));
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





