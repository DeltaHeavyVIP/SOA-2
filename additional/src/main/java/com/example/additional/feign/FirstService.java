package com.example.additional.feign;

import com.example.objects.common.FilterDto;
import com.example.objects.common.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "first-service", url = "https://localhost:10102/api/v1")
public interface FirstService {
    String URL_GET_PRODUCT_BY_FILTER = "/products/filter";
    String URL_UPDATE_PRODUCT = "/products/{id}";

    @PostMapping(URL_GET_PRODUCT_BY_FILTER)
    List<ProductDto> getProductsByFilter(@RequestBody FilterDto filterRequestDto);

    @PutMapping(URL_UPDATE_PRODUCT)
    ProductDto updateProductById(@PathVariable Integer id, @RequestBody ProductDto productRequestDto);

}
