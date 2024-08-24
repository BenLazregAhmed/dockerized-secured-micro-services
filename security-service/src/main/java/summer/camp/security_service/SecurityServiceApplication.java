package summer.camp.security_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import summer.camp.security_service.entities.AppRole;
import summer.camp.security_service.entities.AppUser;
import summer.camp.security_service.service.AccountService;

@SpringBootApplication
public class SecurityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityServiceApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner start(AccountService accountService)
	{
		return args -> {
			accountService.addNewRole(AppRole.builder().roleName("USER").build());
			accountService.addNewRole(AppRole.builder().roleName("ADMIN").build());
			accountService.addNewRole(AppRole.builder().roleName("CUSTOMER_MANGER").build());
			accountService.addNewRole(AppRole.builder().roleName("PRODUCT_MANGER").build());
			accountService.addNewRole(AppRole.builder().roleName("BILLS_MANGER").build());

			accountService.addNewUser(AppUser.builder().username("admin").password("1234").build());
			accountService.addNewUser(AppUser.builder().username("user1").password("1234").build());
			accountService.addNewUser(AppUser.builder().username("user2").password("1234").build());
			accountService.addNewUser(AppUser.builder().username("user3").password("1234").build());
			accountService.addNewUser(AppUser.builder().username("user4").password("1234").build());

			accountService.addRoleToUser("user1","USER");
			accountService.addRoleToUser("user2","USER");
			accountService.addRoleToUser("user2","CUSTOMER_MANGER");
			accountService.addRoleToUser("user3","USER");
			accountService.addRoleToUser("user3","PRODUCT_MANGER");
			accountService.addRoleToUser("user4","BILLS_MANGER");
			accountService.addRoleToUser("user4","USER");
			accountService.addRoleToUser("admin","USER");
			accountService.addRoleToUser("admin","ADMIN");
		};
	}

}
