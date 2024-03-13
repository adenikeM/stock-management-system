package com.techfirm.stock.service;

import com.techfirm.stock.model.Product;
import com.techfirm.stock.model.ProductSaleRequest;
import com.techfirm.stock.model.Sale;
import com.techfirm.stock.repository.ProductRepository;
import com.techfirm.stock.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    public SaleService(SaleRepository saleRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }
    public List<Sale> getAllSale(){
        return saleRepository.findAll();
    }
    public Optional<Sale> getSale(Integer id){ return saleRepository.findById(id);}
    public Sale createSale(Sale sale){
        List<Product> productList = savedProductsWithRepo(sale.getProducts());
        sale.setProducts(productList);
        return saleRepository.save(sale);
    }
    private List<Product> savedProductsWithRepo(List<Product> products) {
        return productRepository.saveAll(products);
    }
    public Optional<Sale> UpdateSale(Sale sale){
        saleRepository.findById(sale.getId());
        if(sale.getId() == null){
            throw  new IllegalArgumentException("Sale id cannot be null");
        }
        List<Product> productList = savedProductsWithRepo(sale.getProducts());
        sale.setProducts(productList);
        return Optional.of(saleRepository.save(sale));
    }
//    public Sale UpdateSale2(ProductSaleRequest sale){
//        Sale fetchedSale =  saleRepository.findById(sale.getProductId()). orElseThrow(() -> new RuntimeException());
//      fetchedSale.setQuantitySold(fetchedSale.getQuantitySold() + sale.getQuantity());
//      return saleRepository.save(fetchedSale);
//    }
//
    public void deleteSale(Integer id){
        saleRepository.findById(id);
    }
}
