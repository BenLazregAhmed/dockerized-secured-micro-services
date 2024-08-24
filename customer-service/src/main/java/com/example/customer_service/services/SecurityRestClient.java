package com.example.customer_service.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SECURITY-SERVICE")
public interface SecurityRestClient {
    @GetMapping(path = "/userDetails/{username}")
    public UserDetails getUserDetails(@PathVariable String username);
}