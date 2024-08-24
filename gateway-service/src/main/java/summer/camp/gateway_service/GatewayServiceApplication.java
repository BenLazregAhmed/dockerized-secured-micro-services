package summer.camp.gateway_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	//configuration statique des routes avec java
	//@Bean
	RouteLocator routeLocator(RouteLocatorBuilder builder)
	{
		return builder.routes()
				.route(r->r.path("/customers/**").uri("http://localhost:8090"))
				.route(r->r.path("/products/**").uri("http://localhost:8085"))
				.build();
	}
	//configuration statique des routes avec discovery

	//@Bean
	RouteLocator routeLocatorDiscovery(RouteLocatorBuilder builder)
	{
		return builder.routes()
				.route(r->r.path("/customers/**").uri("lb://CUSTOMER-SERVICE"))
				.route(r->r.path("/products/**").uri("lb://PRODUCT-SERVICE"))
				.build();
	}
	//configuration dynamique
	@Bean
	DiscoveryClientRouteDefinitionLocator dynamicRoutes(ReactiveDiscoveryClient rdc,
														DiscoveryLocatorProperties dlp){
		return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);
	}
}
