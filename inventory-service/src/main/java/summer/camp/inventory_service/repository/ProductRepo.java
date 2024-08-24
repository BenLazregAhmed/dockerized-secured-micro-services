package summer.camp.inventory_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import summer.camp.inventory_service.entity.Product;

@RepositoryRestResource
public interface ProductRepo extends JpaRepository<Product, Long> {
}