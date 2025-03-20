package br.com.app.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ProductRequestDto {
    @NotBlank(message = "name é obrigatório")
    private String name;
    private String description;
    @NotNull(message = "price é obrigatório")
    private BigDecimal price;
    @NotBlank(message = "category é obrigatório")
    private String category;
    @NotNull(message = "stockQuantity é obrigatório")
    private Integer stockQuantity;
}
