package com.example.rqchallenge.employees.config;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.glassfish.jersey.client.ClientProperties.CONNECT_TIMEOUT;
import static org.glassfish.jersey.client.ClientProperties.READ_TIMEOUT;

@Configuration
@ComponentScan
@ConfigurationProperties(prefix = "dummy-service")
@Getter
@Setter
public class DummyServiceConfig {

    private String getEmployeesUrl;
    private String getEmployeeUrl;
    private String deleteEmployeeUrl;
    private String createEmployeeUrl;
    private Integer connectTimeout;
    private Integer readTimeout;

    @Bean
    public Client client() {
        return ClientBuilder.newClient()
                .property(CONNECT_TIMEOUT, connectTimeout).property(READ_TIMEOUT, readTimeout);
    }

}
