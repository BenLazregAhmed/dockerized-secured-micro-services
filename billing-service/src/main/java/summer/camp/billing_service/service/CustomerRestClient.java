package summer.camp.billing_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import summer.camp.billing_service.model.Customer;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerRestClient {
    @GetMapping(path = "/customers/{id}")
    public Customer getCustomerById(@PathVariable Long id, @RequestHeader(name = "Authorization")String jwt);
}
