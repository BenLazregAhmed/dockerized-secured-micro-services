package com.example.customer_service.security;

import com.example.customer_service.services.SecurityRestClient;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private SecurityRestClient securityRestClient;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return securityRestClient.getUserDetails(username);
    }
}
