package summer.camp.billing_service.service;

import jakarta.ws.rs.QueryParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import summer.camp.billing_service.model.Customer;
import summer.camp.billing_service.model.Product;

@FeignClient(name = "INVENTORY-SERVICE")
public interface ProductRestClient {
    @GetMapping(path = "/products")
    public PagedModel<Product> products(@RequestHeader(name = "Authorization")String jwt);
    @GetMapping(path = "/products/{id}")
    public Product getProductById(@PathVariable Long id,@RequestHeader(name = "Authorization")String jwt);
}
