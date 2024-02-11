package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.Product;
import com.ecommerce.ecommerce.errors.Messages;
import com.ecommerce.ecommerce.request.ProductCreateRequest;
import com.ecommerce.ecommerce.request.ProductUpdateRequest;
import com.ecommerce.ecommerce.response.ProductDiscountOrTaxUpdateResponse;
import com.ecommerce.ecommerce.response.ProductResponse;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EcommerceServiceTest {

    private String PRODUCT_ID = "M123";
    private String NAME = "abc";
    private String DESCRIPTION = "desc";
    private double PRICE = 100.0;
    private long QUANTITY = 53;
    private String NAME1 = "abc1";
    private String DESCRIPTION1 = "desc1";

    @InjectMocks
    private EcommerceServiceImpl ecommerceService;

    public EcommerceServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createProductTest() throws Exception {
        ProductCreateRequest productCreateRequest = new ProductCreateRequest();
        productCreateRequest.setName(NAME);
        productCreateRequest.setDescription(DESCRIPTION);
        productCreateRequest.setPrice(PRICE);
        productCreateRequest.setQuantity(QUANTITY);

        ProductResponse productResponse = ecommerceService.createProduct(productCreateRequest);

        assertEquals(NAME, productResponse.getName());
        assertEquals(DESCRIPTION, productResponse.getDescription());
        assertEquals(PRICE, productResponse.getPrice());
        assertEquals(QUANTITY, productResponse.getQuantity());
    }

    @Test(expected = Exception.class)
    public void createProductProductAlreadyExistsTest() throws Exception {
        Set<String> productNameSet = new HashSet<>();
        productNameSet.add(NAME);
        ecommerceService.setProductNameSet(productNameSet);
        ProductCreateRequest productCreateRequest = new ProductCreateRequest();
        productCreateRequest.setName(NAME);
        productCreateRequest.setDescription(DESCRIPTION);
        productCreateRequest.setPrice(PRICE);
        productCreateRequest.setQuantity(QUANTITY);

        ProductResponse productResponse = ecommerceService.createProduct(productCreateRequest);
    }

    @Test
    public void getProductTest() throws Exception {
        Set<String> productNameSet = new HashSet<>();
        productNameSet.add(NAME);
        ecommerceService.setProductNameSet(productNameSet);
        Map<String, Product> map = new HashMap<>();
        String productId = UUID.randomUUID().toString();
        Product product = new Product();
        product.setProductId(productId);
        product.setName(NAME);
        product.setDescription(DESCRIPTION);
        product.setPrice(PRICE);
        product.setQuantity(QUANTITY);
        map.put(productId, product);
        ecommerceService.setProductIdAndProductMap(map);

        ProductResponse productResponse = ecommerceService.getProduct(productId);
        assertEquals(product.getProductId(), productResponse.getProductId());
    }

    @Test(expected = Exception.class)
    public void getProductProductNotFoundExceptionTest() throws Exception {
        Map<String, Product> map = new HashMap<>();
        String productId = UUID.randomUUID().toString();
        Product product = new Product();
        product.setProductId(productId);
        product.setName(NAME);
        product.setDescription(DESCRIPTION);
        product.setPrice(PRICE);
        product.setQuantity(QUANTITY);
        map.put("test", product);
        ecommerceService.setProductIdAndProductMap(map);

        ProductResponse productResponse = ecommerceService.getProduct(productId);
    }

    @Test
    public void updateProductTest() throws Exception {
        Set<String> productNameSet = new HashSet<>();
        productNameSet.add(NAME);
        ecommerceService.setProductNameSet(productNameSet);
        Map<String, Product> map = new HashMap<>();
        String productId = UUID.randomUUID().toString();
        Product product = new Product();
        product.setProductId(productId);
        product.setName(NAME);
        product.setDescription(DESCRIPTION);
        product.setPrice(PRICE);
        product.setQuantity(QUANTITY);
        map.put(productId, product);
        ecommerceService.setProductIdAndProductMap(map);

        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
        productUpdateRequest.setProductId(productId);
        productUpdateRequest.setName(NAME1);
        productUpdateRequest.setDescription(DESCRIPTION1);

        String response = ecommerceService.updateProduct(productUpdateRequest);

        assertEquals(Messages.PRODUCT_SUCCESSFULLY_UPDATED, response);
    }

    @Test
    public void updateProductFailureTest() throws Exception {
        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
        productUpdateRequest.setProductId(PRODUCT_ID);
        productUpdateRequest.setName(NAME1);
        productUpdateRequest.setDescription(DESCRIPTION1);

        String response = ecommerceService.updateProduct(productUpdateRequest);

        assertEquals(Messages.PRODUCT_UPDATE_FAILED + Messages.SPACE + Messages.PRODUCT_NOT_FOUND, response);
    }

    @Test
    public void deleteProductTest() throws Exception {
        Set<String> productNameSet = new HashSet<>();
        productNameSet.add(NAME);
        ecommerceService.setProductNameSet(productNameSet);
        Map<String, Product> map = new HashMap<>();
        String productId = UUID.randomUUID().toString();
        Product product = new Product();
        product.setProductId(productId);
        product.setName(NAME);
        product.setDescription(DESCRIPTION);
        product.setPrice(PRICE);
        product.setQuantity(QUANTITY);
        map.put(productId, product);
        ecommerceService.setProductIdAndProductMap(map);

        String response = ecommerceService.deleteProduct(productId);

        assertEquals(Messages.SUCCESSFULLY_PRODUCT_DELETE, response);
    }

    @Test
    public void deleteProductFailureTest() throws Exception {
        String response = ecommerceService.deleteProduct(PRODUCT_ID);
        assertEquals(Messages.PRODUCT_DELETION_FAILED + Messages.SPACE + Messages.PRODUCT_NOT_FOUND, response);
    }

    @Test
    public void updateProductDiscountOrTaxTest() throws Exception {
        Set<String> productNameSet = new HashSet<>();
        productNameSet.add(NAME);
        ecommerceService.setProductNameSet(productNameSet);
        Map<String, Product> map = new HashMap<>();
        String productId = UUID.randomUUID().toString();
        Product product = new Product();
        product.setProductId(productId);
        product.setName(NAME);
        product.setDescription(DESCRIPTION);
        product.setPrice(PRICE);
        product.setQuantity(QUANTITY);
        map.put(productId, product);
        ecommerceService.setProductIdAndProductMap(map);

        ProductDiscountOrTaxUpdateResponse response = ecommerceService.updateProductDiscountOrTax(productId, "discount", 10);

        assertEquals(PRICE - ((10/100)*PRICE), response.getPrice());
    }

    @Test
    public void updateProductDiscountOrTaxFailureTest() throws Exception {
        Set<String> productNameSet = new HashSet<>();
        productNameSet.add(NAME);
        ecommerceService.setProductNameSet(productNameSet);
        Map<String, Product> map = new HashMap<>();
        String productId = UUID.randomUUID().toString();
        Product product = new Product();
        product.setProductId(productId);
        product.setName(NAME);
        product.setDescription(DESCRIPTION);
        product.setPrice(PRICE);
        product.setQuantity(QUANTITY);
        map.put(productId, product);
        ecommerceService.setProductIdAndProductMap(map);

        ProductDiscountOrTaxUpdateResponse response = ecommerceService.updateProductDiscountOrTax(productId, "abc", 10);

        assertEquals(Messages.PRODUCT_UPDATE_FAILED + Messages.SPACE + Messages.INVALID_INPUT, response.getOutputMessage());
    }
}
