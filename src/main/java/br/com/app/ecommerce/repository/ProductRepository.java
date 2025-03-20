package br.com.app.ecommerce.repository;

import br.com.app.ecommerce.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findByName(String name);
}
