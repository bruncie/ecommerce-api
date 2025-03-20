package br.com.app.ecommerce.service;

import br.com.app.ecommerce.dto.ProductRequestDto;
import br.com.app.ecommerce.dto.ProductResponseDto;
import br.com.app.ecommerce.dto.ProductUpdateRequestDto;
import br.com.app.ecommerce.entity.ProductEntity;
import br.com.app.ecommerce.exception.BadRequestException;
import br.com.app.ecommerce.exception.ResourceNotFoundException;
import br.com.app.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(ProductRequestDto userRequestDTO) {
        var entity = this.mapToEntity(userRequestDTO);
        return this.mapToDTO(productRepository.save(entity));
    }

    public ProductResponseDto updateProduct(UUID id, ProductRequestDto dto) {
        if (!productRepository.existsById(id))
            throw new ResourceNotFoundException("Produto não encontrado");

        ProductEntity updatedProduct = productRepository.save(ProductEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .stockQuantity(dto.getStockQuantity())
                .updatedAt(LocalDateTime.now())
                .build());
        return mapToDTO(updatedProduct);
    }

    public List<ProductResponseDto> findByProductName(String productName) {
        if (productName.isBlank())
            throw new BadRequestException("Informe um productName");

        return mapToDTOList(this.validateFindProductbyName(productName, false));
    }

    public List<ProductResponseDto> findAllProduct() {
        return mapToDTOList(this.validateFindProductbyName(null, true));
    }

    public String deleteProduct(UUID id) {
        if (!productRepository.existsById(id))
            throw new ResourceNotFoundException("Não foi encontrado o produto informado");
        productRepository.deleteById(id);
        return "Produto deletado";
    }

    private List<ProductEntity> validateFindProductbyName(String productName, boolean isFindAll) {
        List<ProductEntity> product;
        String msg = "Não foi encontrado nenhum produto";

        if (isFindAll) {
            product = productRepository.findAll();
            if (product.isEmpty())
                throw new ResourceNotFoundException(msg);
        } else {
            product = productRepository.findByName(productName);
            if (product.isEmpty())
                throw new ResourceNotFoundException(msg);
        }
        return product;
    }

    private ProductEntity mapToEntity(ProductRequestDto userRequestDTO) {
        return ProductEntity.builder()
                .name(userRequestDTO.getName())
                .description(userRequestDTO.getDescription())
                .price(userRequestDTO.getPrice())
                .category(userRequestDTO.getCategory())
                .stockQuantity(userRequestDTO.getStockQuantity())
                .createdAt(LocalDateTime.now())
                .build();
    }

    private ProductResponseDto mapToDTO(ProductEntity entity) {
        return ProductResponseDto.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .description(entity.getDescription())
                    .price(entity.getPrice())
                    .category(entity.getCategory())
                    .stockQuantity(entity.getStockQuantity())
                    .createdAt(entity.getCreatedAt())
                    .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private List<ProductResponseDto> mapToDTOList(List<ProductEntity> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return List.of();
        }

        return entityList.stream()
            .map(entity -> ProductResponseDto.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .description(entity.getDescription())
                    .price(entity.getPrice())
                    .category(entity.getCategory())
                    .stockQuantity(entity.getStockQuantity())
                    .createdAt(entity.getCreatedAt())
                    .updatedAt(entity.getUpdatedAt())
                    .build())
            .collect(Collectors.toList());
    }
}