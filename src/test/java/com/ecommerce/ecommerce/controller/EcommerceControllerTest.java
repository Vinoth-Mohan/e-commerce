package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.errors.Messages;
import com.ecommerce.ecommerce.request.ProductCreateRequest;
import com.ecommerce.ecommerce.request.ProductUpdateRequest;
import com.ecommerce.ecommerce.response.ProductDiscountOrTaxUpdateResponse;
import com.ecommerce.ecommerce.response.ProductResponse;
import com.ecommerce.ecommerce.service.EcommerceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = EcommerceController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class EcommerceControllerTest {

    private String PRODUCT_ID = "M123";
    private String NAME = "abc";
    private String DESCRIPTION = "desc";
    private double PRICE = 25.7;
    private long QUANTITY = 53;
    private String NAME1 = "abc1";

    @MockBean
    private EcommerceService ecommerceService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createProductTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductCreateRequest productCreateRequest = new ProductCreateRequest();
        productCreateRequest.setName(NAME);
        productCreateRequest.setDescription(DESCRIPTION);
        productCreateRequest.setPrice(PRICE);
        productCreateRequest.setQuantity(QUANTITY);

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(PRODUCT_ID);
        productResponse.setName(NAME);
        productResponse.setDescription(DESCRIPTION);
        productResponse.setPrice(PRICE);
        productResponse.setQuantity(QUANTITY);

        Mockito.when(ecommerceService.createProduct(Mockito.any())).thenReturn(productResponse);

        ResultActions resultActions = mockMvc.perform(post("api/ecommerce/create")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(productCreateRequest)));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(NAME)));

        Mockito.verify(ecommerceService).createProduct(Mockito.any());
    }

    @Test
    public void createProductExceptionTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductCreateRequest productCreateRequest = new ProductCreateRequest();
        productCreateRequest.setName(NAME);
        productCreateRequest.setDescription(DESCRIPTION);
        productCreateRequest.setPrice(-1);
        productCreateRequest.setQuantity(QUANTITY);

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(PRODUCT_ID);
        productResponse.setName(NAME);
        productResponse.setDescription(DESCRIPTION);
        productResponse.setPrice(PRICE);
        productResponse.setQuantity(QUANTITY);

        Mockito.when(ecommerceService.createProduct(Mockito.any())).thenReturn(productResponse);

        ResultActions resultActions = mockMvc.perform(post("api/ecommerce/create")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(productCreateRequest)));

        resultActions.andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    public void updateProductTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
        productUpdateRequest.setProductId(PRODUCT_ID);
        productUpdateRequest.setName(NAME1);
        productUpdateRequest.setDescription(DESCRIPTION);
        productUpdateRequest.setPrice(PRICE);
        productUpdateRequest.setQuantity(QUANTITY);

        Mockito.when(ecommerceService.updateProduct(Mockito.any())).thenReturn(Messages.PRODUCT_SUCCESSFULLY_UPDATED);

        ResultActions resultActions = mockMvc.perform(put("/api/ecommerce/update")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(productUpdateRequest)));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(ecommerceService).updateProduct(Mockito.any());
    }

    @Test
    public void updateProductExceptionTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
        productUpdateRequest.setProductId(PRODUCT_ID);
        productUpdateRequest.setName(NAME1);
        productUpdateRequest.setDescription(DESCRIPTION);
        productUpdateRequest.setPrice(PRICE);
        productUpdateRequest.setQuantity(-1);

        Mockito.when(ecommerceService.updateProduct(Mockito.any())).thenReturn(Messages.PRODUCT_SUCCESSFULLY_UPDATED);

        ResultActions resultActions = mockMvc.perform(put("/api/ecommerce/update")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(productUpdateRequest)));

        resultActions.andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    public void getProductTest() throws Exception {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(PRODUCT_ID);
        productResponse.setName(NAME);

        Mockito.when(ecommerceService.getProduct(Mockito.any())).thenReturn(productResponse);

        ResultActions resultActions = mockMvc.perform(get("/api/ecommerce/get/M123")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(NAME)));

        Mockito.verify(ecommerceService).getProduct(Mockito.any());
    }

    @Test
    public void getDeleteTest() throws Exception {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(PRODUCT_ID);
        productResponse.setName(NAME);

        Mockito.when(ecommerceService.getProduct(Mockito.any())).thenReturn(productResponse);

        ResultActions resultActions = mockMvc.perform(delete("/api/ecommerce/delete/M123")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(ecommerceService).deleteProduct(Mockito.any());
    }

    @Test
    public void updateDiscountOrTaxTest() throws Exception {
        ProductDiscountOrTaxUpdateResponse productResponse = new ProductDiscountOrTaxUpdateResponse();
        productResponse.setProductId(PRODUCT_ID);
        productResponse.setName(NAME);
        productResponse.setOutputMessage(Messages.PRODUCT_SUCCESSFULLY_UPDATED);

        Mockito.when(ecommerceService.updateProductDiscountOrTax(Mockito.any(), Mockito.any(), Mockito.anyInt())).thenReturn(productResponse);

        ResultActions resultActions = mockMvc.perform(put("/api/ecommerce/updateDiscountOrTax/M123/discount/10")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.outputMessage", CoreMatchers.is(Messages.PRODUCT_SUCCESSFULLY_UPDATED)));

        Mockito.verify(ecommerceService).updateProductDiscountOrTax(Mockito.any(), Mockito.any(), Mockito.anyInt());
    }

    @Test
    public void updateDiscountOrTaxExceptionTest() throws Exception {
        ProductDiscountOrTaxUpdateResponse productResponse = new ProductDiscountOrTaxUpdateResponse();
        productResponse.setProductId(PRODUCT_ID);
        productResponse.setName(NAME);
        productResponse.setOutputMessage(Messages.PRODUCT_SUCCESSFULLY_UPDATED);

        Mockito.when(ecommerceService.updateProductDiscountOrTax(Mockito.any(), Mockito.any(), Mockito.anyInt())).thenReturn(productResponse);

        ResultActions resultActions = mockMvc.perform(put("/api/ecommerce/updateDiscountOrTax/M123/discount/1000")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions.andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

}
