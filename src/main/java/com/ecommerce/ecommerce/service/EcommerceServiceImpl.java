package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.Product;
import com.ecommerce.ecommerce.messages.Messages;
import com.ecommerce.ecommerce.request.ProductCreateRequest;
import com.ecommerce.ecommerce.request.ProductUpdateRequest;
import com.ecommerce.ecommerce.response.ProductDiscountOrTaxUpdateResponse;
import com.ecommerce.ecommerce.response.ProductResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EcommerceServiceImpl implements EcommerceService {

    private String DISCOUNT = "discount";
    private String TAX = "tax";

    public Map<String, Product> productIdAndProductMap = new HashMap<>();
    private Set<String> productNameSet = new HashSet<>();

    public Map<String, Product> getProductIdAndProductMap() {
        return productIdAndProductMap;
    }

    public void setProductIdAndProductMap(Map<String, Product> productIdAndProductMap) {
        this.productIdAndProductMap = productIdAndProductMap;
    }

    public Set<String> getProductNameSet() {
        return productNameSet;
    }

    public void setProductNameSet(Set<String> productNameSet) {
        this.productNameSet = productNameSet;
    }

    @Override
    public ProductResponse createProduct(ProductCreateRequest productCreateRequest) throws Exception {
        if (productNameSet.contains(productCreateRequest.getName())) {
            throw new Exception(Messages.PRODUCT_ALREADY_EXISTS);
        }
        Product product = new Product();
        BeanUtils.copyProperties(productCreateRequest, product);
        product.setProductId(UUID.randomUUID().toString());
        productIdAndProductMap.put(product.getProductId(), product);
        productNameSet.add(product.getName());
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);
        return productResponse;
    }

    @Override
    public ProductResponse getProduct(String productId) throws Exception {
        if (productIdAndProductMap.containsKey(productId)) {
            ProductResponse productResponse = new ProductResponse();
            BeanUtils.copyProperties(productIdAndProductMap.get(productId), productResponse);
            return productResponse;
        } else {
            throw new Exception(Messages.PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public String updateProduct(ProductUpdateRequest productUpdateRequest) throws Exception {
        if (!productIdAndProductMap.containsKey(productUpdateRequest.getProductId())) {
            return Messages.PRODUCT_UPDATE_FAILED + Messages.SPACE + Messages.PRODUCT_NOT_FOUND;
        }
        Product product = productIdAndProductMap.get(productUpdateRequest.getProductId());
        BeanUtils.copyProperties(productUpdateRequest, product, "productId");
        return Messages.PRODUCT_SUCCESSFULLY_UPDATED;
    }

    @Override
    public String deleteProduct(String productId) throws Exception {
        if (!productIdAndProductMap.containsKey(productId)) {
            return Messages.PRODUCT_DELETION_FAILED + Messages.SPACE + Messages.PRODUCT_NOT_FOUND;
        }
        productIdAndProductMap.remove(productId);
        return Messages.SUCCESSFULLY_PRODUCT_DELETE;
    }

    @Override
    public ProductDiscountOrTaxUpdateResponse updateProductDiscountOrTax(String productId, String discountOrTax, int percentage) throws Exception {
        if (!productIdAndProductMap.containsKey(productId)) {
            throw new Exception(Messages.PRODUCT_NOT_FOUND);
        }
        if (discountOrTax.equalsIgnoreCase(DISCOUNT)) {
            Product product = productIdAndProductMap.get(productId);
            double price = product.getPrice();
            double discount = (percentage / 100.0) * price;
            price -= discount;
            product.setPrice(price);
            productIdAndProductMap.put(productId, product);
            ProductDiscountOrTaxUpdateResponse response = new ProductDiscountOrTaxUpdateResponse();
            BeanUtils.copyProperties(product, response);
            response.setOutputMessage(Messages.PRODUCT_SUCCESSFULLY_UPDATED);
            return response;
        } else if (discountOrTax.equalsIgnoreCase(TAX)) {
            Product product = productIdAndProductMap.get(productId);
            double price = product.getPrice();
            double tax = (percentage / 100.0) * price;
            price += tax;
            product.setPrice(price);
            productIdAndProductMap.put(productId, product);
            ProductDiscountOrTaxUpdateResponse response = new ProductDiscountOrTaxUpdateResponse();
            BeanUtils.copyProperties(product, response);
            response.setOutputMessage(Messages.PRODUCT_SUCCESSFULLY_UPDATED);
            return response;
        } else {
            ProductDiscountOrTaxUpdateResponse response = new ProductDiscountOrTaxUpdateResponse();
            Product product = productIdAndProductMap.get(productId);
            BeanUtils.copyProperties(product, response);
            response.setOutputMessage(Messages.PRODUCT_UPDATE_FAILED + Messages.SPACE + Messages.INVALID_INPUT);
            return response;
        }
    }

}
