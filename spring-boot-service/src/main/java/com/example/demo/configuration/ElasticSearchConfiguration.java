package com.example.demo.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories
public class ElasticSearchConfiguration {
    public String getConnectionString() {
        return "http://localhost:9200";  // Adjust the host if needed
    }
}
