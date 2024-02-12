package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.messages.Messages;
import com.ecommerce.ecommerce.request.ProductCreateRequest;
import com.ecommerce.ecommerce.request.ProductUpdateRequest;
import com.ecommerce.ecommerce.response.ProductDiscountOrTaxUpdateResponse;
import com.ecommerce.ecommerce.response.ProductResponse;
import com.ecommerce.ecommerce.service.EcommerceService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/ecommerce")
public class EcommerceController {

    @Autowired
    private EcommerceService ecommerceService;

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponse createProduct(@RequestBody ProductCreateRequest productCreateRequest) throws Exception {
        checkProductCreateRequest(productCreateRequest);
        return ecommerceService.createProduct(productCreateRequest);
    }

    private static void checkProductCreateRequest(ProductCreateRequest productCreateRequest) throws Exception {
        if (Strings.isEmpty(productCreateRequest.getName())) {
            throw new Exception(Messages.PRODUCT_NAME_MUST_NOT_BE_NULL_OR_EMPTY);
        }
        if (Strings.isEmpty(productCreateRequest.getDescription())) {
            throw new Exception(Messages.PRODUCT_DESCRIPTION_MUST_NOT_BE_NULL_OR_EMPTY);
        }
        if (productCreateRequest.getPrice() <= 0) {
            throw new Exception(Messages.PRODUCT_PRICE_MUST_BE_GREATER_THAN_ZERO);
        }
        if (productCreateRequest.getQuantity() < 0) {
            throw new Exception(Messages.PRODUCT_QUANTITY_MUST_NOT_BE_LESS_THAN_ZERO);
        }
    }

    @GetMapping(value = "get/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponse getProduct(@PathVariable String productId) throws Exception {
        return ecommerceService.getProduct(productId);
    }

    @PutMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateProduct(@RequestBody ProductUpdateRequest productUpdateRequest) throws Exception {
        checkProductUpdateRequest(productUpdateRequest);
        return ecommerceService.updateProduct(productUpdateRequest);
    }

    private static void checkProductUpdateRequest(ProductUpdateRequest productUpdateRequest) throws Exception {
        if (Strings.isEmpty(productUpdateRequest.getProductId())) {
            throw new Exception(Messages.PRODUCT_ID_MUST_NOT_BE_NULL_OR_EMPTY);
        }
        if (Strings.isEmpty(productUpdateRequest.getName())) {
            throw new Exception(Messages.PRODUCT_NAME_MUST_NOT_BE_NULL_OR_EMPTY);
        }
        if (Strings.isEmpty(productUpdateRequest.getDescription())) {
            throw new Exception(Messages.PRODUCT_DESCRIPTION_MUST_NOT_BE_NULL_OR_EMPTY);
        }
        if (productUpdateRequest.getPrice() <= 0) {
            throw new Exception(Messages.PRODUCT_PRICE_MUST_BE_GREATER_THAN_ZERO);
        }
        if (productUpdateRequest.getQuantity() < 0) {
            throw new Exception(Messages.PRODUCT_QUANTITY_MUST_NOT_BE_LESS_THAN_ZERO);
        }
    }

    @DeleteMapping(value = "delete/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteProduct(@PathVariable String productId) throws Exception {
        return ecommerceService.deleteProduct(productId);
    }

    @PutMapping(value = "updateDiscountOrTax/{productId}/{discountOrTax}/{percentage}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDiscountOrTaxUpdateResponse updateDiscountOrTax(@PathVariable String productId, @PathVariable String discountOrTax, @PathVariable String percentage) throws Exception {
        int percent = Integer.parseInt(percentage);
        if (percent < 0 || percent > 100) {
            throw  new Exception(Messages.PERCENTAGE_MUST_BE_BETWEEN_0_AND_100);
        }
        return ecommerceService.updateProductDiscountOrTax(productId, discountOrTax, percent);
    }

}
