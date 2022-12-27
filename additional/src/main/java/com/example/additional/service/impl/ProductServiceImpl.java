package com.example.additional.service.impl;

import com.example.additional.feign.FirstService;
import com.example.additional.service.ProductService;
import com.example.objects.common.FilterDto;
import com.example.objects.common.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.additional.utils.Validator.decreasePercentIsValid;
import static com.example.additional.utils.Validator.responseIdIsEmpty;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Value("${secret.value}")
    private int secretValue;

    private final FirstService firstService;

    public List<ProductDto> getAllProductsManufacture(String manufacturerId) {
        FilterDto filterRequestDto = getFilterWithSecretValue();
        filterRequestDto.setOwnerPassportId(manufacturerId);
        List<ProductDto> response = firstService.getProductsByFilter(filterRequestDto);
        responseIdIsEmpty(response);
        return response;
    }

    public void reducePriceAllProductsByPercentage(Integer decreasePercent) {
        decreasePercentIsValid(decreasePercent);
        List<ProductDto> allProduct = firstService.getProductsByFilter(getFilterWithSecretValue());
        for (ProductDto productResponseDto : allProduct) {
            productResponseDto.setPrice((long) Math.ceil((double) productResponseDto.getPrice() * (100 - decreasePercent) / 100));
            firstService.updateProductById(productResponseDto.getId(), productResponseDto);
        }
    }

    private FilterDto getFilterWithSecretValue() {
        FilterDto filterRequestDto = new FilterDto();
        filterRequestDto.setPage(secretValue);
        filterRequestDto.setPageSize(secretValue);
        return filterRequestDto;
    }
}
