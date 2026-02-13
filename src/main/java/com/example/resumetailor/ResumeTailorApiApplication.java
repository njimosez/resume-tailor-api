package com.example.resumetailor;

import com.example.resumetailor.config.TavilyProperties;

import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(TavilyProperties.class)
public class ResumeTailorApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResumeTailorApiApplication.class, args);
    }
    
   @Bean
   SimpleLoggerAdvisor simpleLoggerAdvisor() {
	   return new SimpleLoggerAdvisor();
   }
}
