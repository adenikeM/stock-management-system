package com.techfirm.stock.service;

import com.techfirm.stock.model.*;
import com.techfirm.stock.model.dto.*;
import com.techfirm.stock.repository.AddressRepository;
import com.techfirm.stock.repository.CustomerInfoRepository;
import com.techfirm.stock.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.techfirm.stock.utils.ObjectMapper.mapCreateProductDTOToProduct;
import static com.techfirm.stock.utils.ObjectMapper.mapUpdateProductDTOToProduct;

@Service
@Slf4j
@RequiredArgsConstructor
//@NoArgsConstructor(force = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryService productCategoryService;
    private final CustomerInfoRepository customerInfoRepository;
    private final SalesService salesService;
    private final AddressRepository addressRepository;

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Page<Product> getAllProduct2(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productRepository.findAll(pageable);
    }

    public Page<Product> getAllProduct3(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> searchProductByFilter(String name, String colour, String size,  int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        return productRepository.findByNameContainingOrColourContainingOrSizeContaining(name, colour, size, pageable);
    }

    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        ProductCategory category = productCategoryService.createProductCategory(product.getProductCategory());
        product.setProductCategory(category);

        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(Product product) {
        Long productId = product.getId();
        if (productId == null) {
            throw new IllegalArgumentException("Product id must not be null");
        }
//        Optional<Product> retrievedProductOptional = productRepository.findById(productId);
//
//        Product retrievedProduct = retrievedProductOptional
//                .orElseThrow(() -> new IllegalArgumentException("Could not retrieve Product with id " + productId));

        Product retrievedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Could not retrieve Product with id " + productId));


        ProductCategory category = productCategoryService.createProductCategory(retrievedProduct.getProductCategory());
        product.setProductCategory(category);

        return Optional.of(productRepository.save(product));
    }

    public List<Product> increaseStock(List<UpdateStockDTO> updateStockDTOS) {
        List<Long> productIdList = updateStockDTOS
                .stream().map(UpdateStockDTO::getProductId).toList();
        List<Product> products = productRepository.findAllById(productIdList);
        if (products.isEmpty()) {
            throw new IllegalArgumentException("product id is invalid");
        }
        for (UpdateStockDTO updateStockDTO : updateStockDTOS) {
            for (Product product : products) {
                if (Objects.equals(updateStockDTO.getProductId(), product.getId())) {
                    int incrementQuantity = updateStockDTO.getQuantityToBeAdded();
                    product.setAvailableQuantity(product.getAvailableQuantity() + incrementQuantity);
                }
            }
        }
        return productRepository.saveAll(products);
    }

    public void deleteProduct(Long id) {
        productRepository.findById(id);
    }

    public Product createProductV2(ProductDTO createProductDTO) {
        ProductCategory productCategory = productCategoryService.getProductCategoryById(createProductDTO.getProductCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid product category id " + createProductDTO.getProductCategoryId()));
        Product product = mapCreateProductDTOToProduct(createProductDTO, productCategory);
        return productRepository.save(product);
    }

    public Product updateProductV2(Long id, ProductDTO updateProductDTO) {
        Product retrievedProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Could not retrieve Product with id " + id));
        log.info("Retrieved product {}", retrievedProduct);

        Long productCategoryId = updateProductDTO.getProductCategoryId();
        ProductCategory productCategory = productCategoryService.getProductCategoryById(productCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product category id " + productCategoryId));

        mapUpdateProductDTOToProduct(updateProductDTO, productCategory, retrievedProduct);
        log.info("Retrieved product after mapping {}", retrievedProduct);
        return productRepository.save(retrievedProduct);
    }

    public ProductPriceDTO getProductPrice(List<ProductsToBePriced> productsToBePriced) {
        List<Long> productIdList = productsToBePriced
                .stream().map(ProductsToBePriced::getProductId).toList();
        List<Product> products = productRepository.findAllById(productIdList);

        if (products.isEmpty()) {
//            return new ProductPriceDTO(new ArrayList<>(), BigDecimal.ZERO);
            throw new IllegalArgumentException("Invalid product Ids");
        }

        Map<Product, Integer> productMap = new HashMap<>();

        //map product to quantity to be priced
        for (ProductsToBePriced productToBePriced : productsToBePriced) {
            for (Product product : products) {
                if (Objects.equals(productToBePriced.getProductId(), product.getId())) {
                    productMap.put(product, productToBePriced.getQuantity());
                }
            }
        }

        ArrayList<ProductPrice> productPriceList = new ArrayList<>();
        final BigDecimal[] totalPriceArray = {BigDecimal.ZERO};

        productMap.forEach((product, quantity) -> {
            BigDecimal price = product.getPrice().multiply(new BigDecimal(quantity));
            ProductPrice productPrice = new ProductPrice(product.getId(), quantity, price);

            productPriceList.add(productPrice);

            totalPriceArray[0] = totalPriceArray[0].add(price);
        });

        //Another way to create productPriceList
//        productPriceList = productMap.entrySet().stream().map(productQuantityEntry -> {
//            Product product = productQuantityEntry.getKey();
//            Integer quantity = productQuantityEntry.getValue();
//
//            BigDecimal price = product.getPrice().multiply(new BigDecimal(quantity));
//            ProductPrice productPrice = new ProductPrice(product.getId(), quantity, price);
//
//            totalPriceArray[0] = totalPriceArray[0].add(price);
//            return productPrice;
//        }).toList();


        //Can calculate total price from productPriceList
//        BigDecimal salesPrice = productPriceList.stream()
//                .map(ProductPrice::getPrice)
//                .reduce(BigDecimal::add)
//                .orElseThrow();

//        return new ProductPriceDTO(productPriceList, salesPrice);

        return new ProductPriceDTO(productPriceList, totalPriceArray[0]);
    }

    public ProductPriceDTO getProductPriceV2(List<ProductsToBePriced> productsToBePriced) {
        List<Long> productIdList = productsToBePriced
                .stream().map(ProductsToBePriced::getProductId).toList();
        List<Product> products = productRepository.findAllById(productIdList);

        if (products.isEmpty()) {
            throw new IllegalArgumentException("Invalid product Ids");
        }

        ArrayList<ProductPrice> productPriceList = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        //map product to quantity to be priced
        for (ProductsToBePriced productToBePriced : productsToBePriced) {
            for (Product product : products) {
                if (Objects.equals(productToBePriced.getProductId(), product.getId())) {
                    int quantity = productToBePriced.getQuantity();
                    BigDecimal price = product.getPrice().multiply(new BigDecimal(quantity));
                    ProductPrice productPrice = new ProductPrice(product.getId(), quantity, price);
                    productPriceList.add(productPrice);
                    totalPrice = totalPrice.add(price);
                }
            }
        }

        return new ProductPriceDTO(productPriceList, totalPrice);
    }

    @Transactional
    public Sales sellProduct(SellProductsDTO sellProductsDTO) {
        List<ProductsToBePriced> productsToBeSold = sellProductsDTO.getProductsToBeSold();

        //create customer info if not exist or retrieve if exist
        CustomerInfo customerInfo = sellProductsDTO.getCustomerInfo();
        if(customerInfo.getId() == null) {
            Address address = addressRepository.save(customerInfo.getAddress());
            customerInfo.setAddress(address);
            customerInfo = customerInfoRepository.save(customerInfo);
        } else {
            customerInfo = customerInfoRepository.findById(customerInfo.getId()).orElseThrow();
        }

        //retrieve all retrievedProducts by productIdList
        List<Long> productIdList = ensureProductsExistAndGetproductIds(productsToBeSold);
        BigDecimal salesPrice = BigDecimal.ZERO;
        int totalQuantitySold = 0;

        List<Product> retrievedProducts = productRepository.findAllById(productIdList);

        //validate enough quantity
        for (ProductsToBePriced productToBeSold : productsToBeSold) {
            for (Product product : retrievedProducts) {
                if (Objects.equals(product.getId(), productToBeSold.getProductId())) {
                    int quantityToBeSold = productToBeSold.getQuantity();
                    Integer initialAvailableQuantity = product.getAvailableQuantity();
                    if (initialAvailableQuantity >= quantityToBeSold) {
                        //add price to total price
                        salesPrice = salesPrice.add(product.getPrice().multiply(new BigDecimal(quantityToBeSold)));

                        //add up quantity sold
                        totalQuantitySold = totalQuantitySold + quantityToBeSold;

                        //reduce product quantity by quantity sold
                        product.setAvailableQuantity(initialAvailableQuantity - quantityToBeSold);
                    } else {
                        throw new IllegalArgumentException("Insufficient quantity for " + product.getName());
                    }
                }
            }
        }
//        ensureEnoughQuantityAndPrepareSalesData(retrievedProducts, productsToBeSold, salesPrice, totalQuantitySold);
        //update product using repository
        List<Product> updatedProducts = productRepository.saveAll(retrievedProducts);

        //build sales entity
        Sales sales = Sales.builder()
                .customerInfo(customerInfo)
                .products(updatedProducts)
                .price(salesPrice)
                .salesDate(LocalDateTime.now())
                .totalQuantitySold(totalQuantitySold)
                .build();

        //save sales entity and return
        return salesService.createSale(sales);
    }

    private static void ensureEnoughQuantityAndPrepareSalesData(List<Product> retrievedProducts, List<ProductsToBePriced> productsToBeSold, BigDecimal salesPrice, int totalQuantitySold) {

    }

    private List<Long> ensureProductsExistAndGetproductIds(List<ProductsToBePriced> productsToBeSold) {
        List<Long> productIdList = productsToBeSold.stream().map(ProductsToBePriced::getProductId).toList();
        if(!productRepository.existsByIdIn(productIdList)) {
            throw new IllegalArgumentException("Invalid product list");
        }
        return productIdList;
    }
}

