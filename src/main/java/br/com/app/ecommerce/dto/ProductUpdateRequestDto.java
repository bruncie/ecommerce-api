package br.com.app.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductUpdateRequestDto {
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Integer stockQuantity;
}
