package com.ia.knn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan(basePackages = { "com.ia.knn" })
@SpringBootApplication
@EnableSwagger2
public class KnnApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnnApplication.class, args);
	}

}
