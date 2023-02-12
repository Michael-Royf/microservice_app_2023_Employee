package com.michael.employeeService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = {
                "com.michael.employeeService",
                "com.michael.amqp"
        }
)
@EnableFeignClients(basePackages = "com.michael.clients")
public class EmployeeServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceApp.class, args);
    }
}
