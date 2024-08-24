package summer.camp.billing_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import summer.camp.billing_service.entities.Bill;
import summer.camp.billing_service.entities.ProductItem;
import summer.camp.billing_service.model.Customer;
import summer.camp.billing_service.model.Product;
import summer.camp.billing_service.repository.BillRepo;
import summer.camp.billing_service.repository.ProductItemRepo;
import summer.camp.billing_service.service.CustomerRestClient;
import summer.camp.billing_service.service.ProductRestClient;

import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}
	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	@Bean
	CommandLineRunner start(BillRepo billRepo, ProductItemRepo productItemRepo, CustomerRestClient customerRestClient, ProductRestClient productRestClient)
	{
		return args -> {
//			Customer customer1=customerRestClient.getCustomerById(1L);
//			Customer customer2=customerRestClient.getCustomerById(2L);
//			Customer customer3=customerRestClient.getCustomerById(3L);

			Bill b1= Bill.builder()
					.customerId(1L)
					.billingDate(new Date())
					.build();
			Bill b2= Bill.builder()
					.customerId(2L)
					.billingDate(new Date())
					.build();
			Bill b3= Bill.builder()
					.customerId(3L)
					.billingDate(new Date())
					.build();
			billRepo.save(b1);
			billRepo.save(b2);
			billRepo.save(b3);

		};
	}
}
