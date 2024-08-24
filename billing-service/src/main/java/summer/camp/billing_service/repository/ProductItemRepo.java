package summer.camp.billing_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import summer.camp.billing_service.entities.ProductItem;
@RepositoryRestResource
public interface ProductItemRepo extends JpaRepository<ProductItem,Long> {
}
