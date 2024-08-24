package summer.camp.inventory_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import summer.camp.inventory_service.entity.Product;
import summer.camp.inventory_service.repository.ProductRepo;

import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	@Bean
	CommandLineRunner start(ProductRepo productRepository, RepositoryRestConfiguration restConfiguration){
		return args -> {
			restConfiguration.exposeIdsFor(Product.class);
			Random random=new Random();
			for (int i = 1; i <10 ; i++) {
				productRepository.saveAll(List.of(
						Product.builder()
								.name("Computer "+i)
								.price(1200+Math.random()*10000)
								.quantity(1+random.nextInt(200)).build()
				));
			}

		};
	}
}
