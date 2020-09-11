package com.ia.knn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJpaRepositories(basePackages = "com.ia.knn.infrastructure.repository")
@ComponentScan(basePackages = { "com.ia.knn" })
@SpringBootApplication
@EnableSwagger2
public class KnnApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnnApplication.class, args);
	}

}
