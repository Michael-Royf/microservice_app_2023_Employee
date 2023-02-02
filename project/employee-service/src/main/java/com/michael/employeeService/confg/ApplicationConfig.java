package com.michael.employeeService.confg;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

//    @Bean
//    public WebClient webClient() {
//        return WebClient.builder().build();
//    }


//    @Bean
//    public RestTemplate restTemplate(){
//        return  new RestTemplate();
//    }
}
