package com.example.customer_service.repositories;

import com.example.customer_service.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CustomersRepo extends JpaRepository<Customer,Long> {
}
