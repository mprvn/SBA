package com.cts.sba.iiht.projectmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import static com.google.common.collect.Lists.newArrayList;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())	
            .build()
            .apiInfo(apiInfo())
            .useDefaultResponseMessages(false)
            .globalResponseMessage(RequestMethod.GET, newArrayList(new ResponseMessageBuilder().code(500)
                .message("500 message")
                .responseModel(new ModelRef("Error"))
                .build(),
                new ResponseMessageBuilder().code(403)
                    .message("Forbidden!!!!!")
                    .build()));
    }

    private ApiInfo apiInfo() {
    	springfox.documentation.service.Contact contact = new springfox.documentation.service.Contact("Praveen Moolan", "", "mln.prvn@gmail.com");
    	
        ApiInfo apiInfo = new  ApiInfoBuilder().title("Project manager api")
        		.description("This api contains discription of Project Manager Api")
        		.version("1.0")
        		.termsOfServiceUrl("Terms of service")
        		.contact(contact)
        		.license("")
        		.termsOfServiceUrl("")        		
        		.build();
        		
        return apiInfo;
    }
}
