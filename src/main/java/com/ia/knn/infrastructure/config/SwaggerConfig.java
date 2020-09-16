package com.ia.knn.infrastructure.config;

import com.google.common.base.Predicates;
import java.util.concurrent.CompletableFuture;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  private static final String PATH_EXCLUSION_FF4J = "/api/ff4j.*";
  private final SwaggerInfoProps swaggerInfoProps;

  public SwaggerConfig(SwaggerInfoProps swaggerInfoProps) {
    this.swaggerInfoProps = swaggerInfoProps;
  }

  @Bean
  public Docket api() {
    return (new Docket(DocumentationType.SWAGGER_2)).select().apis(
        RequestHandlerSelectors.withClassAnnotation(RestController.class)).paths(PathSelectors.any()).paths(
        Predicates.not(PathSelectors.regex("/api/ff4j.*"))).build().pathMapping("/").genericModelSubstitutes(new Class[]{
        ResponseEntity.class, CompletableFuture.class}).useDefaultResponseMessages(false).apiInfo(this.apiInfo());
  }



  @Bean
  public UiConfiguration swaggerUiConfig() {
    return UiConfigurationBuilder.builder().build();
  }

  protected ApiInfo apiInfo() {
    String title = this.swaggerInfoProps.getTitle();
    String description = this.swaggerInfoProps.getDescription();
    String version = this.swaggerInfoProps.getVersion();
    String contactName = this.swaggerInfoProps.getContactName();
    String contactUrl = this.swaggerInfoProps.getContactUrl();
    String contactEmail = this.swaggerInfoProps.getContactEmail();
    return (new ApiInfoBuilder()).title(title).description(description).version(version).contact(new Contact(contactName, contactUrl, contactEmail)).build();
  }
}
