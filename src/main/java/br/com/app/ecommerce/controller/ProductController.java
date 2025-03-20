package br.com.app.ecommerce.controller;

import br.com.app.ecommerce.dto.ProductRequestDto;
import br.com.app.ecommerce.dto.ProductResponseDto;
import br.com.app.ecommerce.dto.ProductUpdateRequestDto;
import br.com.app.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        return new ResponseEntity<>(productService.createProduct(productRequestDto), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable UUID id,
                                                            @RequestBody ProductRequestDto dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }
    
    @GetMapping("/{productName}")
    public ResponseEntity<List<ProductResponseDto>> findByProduct(@PathVariable String productName) {
        return ResponseEntity.ok(productService.findByProductName(productName));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findAllProducts() {
        return ResponseEntity.ok(productService.findAllProduct());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}