package com.example.customer_service.web;

import com.example.customer_service.entities.Customer;
import com.example.customer_service.repositories.CustomersRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CustomersRestController {
    CustomersRepo customersRepo;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/customers")
    public List<Customer>customersList()
    {
        return customersRepo.findAll();
    }
    @GetMapping(path = "/customer/{id}")
    public Customer getCustomerById(@PathVariable Long id)
    {
        return customersRepo.findById(id).orElse(null);
    }
}
