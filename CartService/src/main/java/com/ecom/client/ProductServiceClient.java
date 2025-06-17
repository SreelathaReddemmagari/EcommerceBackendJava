package com.ecom.client;

import com.ecom.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//product service client which is used to interact with the product service to fetch products
@FeignClient(name = "product-service")
public interface ProductServiceClient {
    @GetMapping("api/product/{id}")
    ProductDto getProductById(@PathVariable("id") Long id);

}
