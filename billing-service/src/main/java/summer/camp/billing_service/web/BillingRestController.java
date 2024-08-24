package summer.camp.billing_service.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import summer.camp.billing_service.entities.Bill;
import summer.camp.billing_service.model.Customer;
import summer.camp.billing_service.repository.BillRepo;
import summer.camp.billing_service.repository.ProductItemRepo;
import summer.camp.billing_service.security.JwtService;
import summer.camp.billing_service.service.CustomerRestClient;
import summer.camp.billing_service.service.ProductRestClient;
@AllArgsConstructor
@RestController
public class BillingRestController {
    BillRepo billRepo;
    ProductRestClient productRestClient;
    ProductItemRepo productItemRepo;
    CustomerRestClient customerRestClient;
    JwtService jwtService;
    @GetMapping(path = "/fullBill/{id}")
    public Bill getBill(@PathVariable(name = "id")Long id)
    {
        Bill bill=billRepo.findById(id).orElseThrow(()->new RuntimeException("BILL NOT FOUND !!"));
        bill.setCustomer(customerRestClient.getCustomerById(bill.getCustomerId(), jwtService.getJwt()));
        bill.getProductItems().forEach(
                (p)->{
                    p.setProduct(productRestClient.getProductById(p.getProductId(), jwtService.getJwt()));
                }
        );
        return bill;
    }
    @GetMapping(path = "/customer/{id}")
    public Customer getCustomer(@PathVariable(name = "id")Long id)
    {
        System.out.println("*******************************************"+jwtService.getJwt());
        return customerRestClient.getCustomerById(id, jwtService.getJwt());
    }
    @GetMapping(path = "/token")
    public String  getJwt()
    {
        return jwtService.getJwt();
    }
}
