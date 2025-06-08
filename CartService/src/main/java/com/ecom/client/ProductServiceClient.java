package com.ecom.client;

import com.ecom.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "http://localhost:8081")
public interface ProductServiceClient {
    @GetMapping("api/product/{id}")
    ProductDto getProductById(@PathVariable("id") Long id);

}
