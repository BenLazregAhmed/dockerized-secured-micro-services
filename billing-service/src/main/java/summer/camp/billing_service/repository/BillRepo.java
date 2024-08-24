package summer.camp.billing_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import summer.camp.billing_service.entities.Bill;
import summer.camp.billing_service.entities.ProductItem;

import java.util.List;

@RepositoryRestResource
public interface BillRepo extends JpaRepository<Bill,Long> {
    //public List<ProductItem>findByBillId(Long id);
}
