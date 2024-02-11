package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.request.ProductCreateRequest;
import com.ecommerce.ecommerce.request.ProductUpdateRequest;
import com.ecommerce.ecommerce.response.ProductDiscountOrTaxUpdateResponse;
import com.ecommerce.ecommerce.response.ProductResponse;

public interface EcommerceService {
    public ProductResponse createProduct(ProductCreateRequest productCreateRequest) throws Exception;

    public ProductResponse getProduct(String productId) throws Exception;

    public String updateProduct(ProductUpdateRequest productUpdateRequest) throws Exception;

    public String deleteProduct(String productId) throws Exception;

    public ProductDiscountOrTaxUpdateResponse updateProductDiscountOrTax(String productId, String discountOrTax, int percentage) throws Exception;
}
