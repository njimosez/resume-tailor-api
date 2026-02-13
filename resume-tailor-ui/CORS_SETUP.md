# CORS Configuration for Resume Tailor API

To enable the React front-end to communicate with the Spring Boot API, you need to add CORS configuration.

## Option 1: Add CORS Configuration Class (Recommended)

Create a new file in `src/main/java/com/example/resumetailor/config/CorsConfig.java`:

```java
package com.example.resumetailor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
```

## Option 2: Add CORS to Controller

Alternatively, add the `@CrossOrigin` annotation to your controller:

```java
@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = "http://localhost:3000")
public class ResumeController {
    // ... existing code
}
```

## Running Both Applications

1. Start the Spring Boot API:
   ```bash
   mvn spring-boot:run
   # Or
   java -jar target/resume-tailor-api-0.0.1-SNAPSHOT.jar
   ```
   The API will run on `http://localhost:8080`

2. Start the React front-end:
   ```bash
   cd resume-tailor-ui
   npm run dev
   ```
   The UI will run on `http://localhost:3000` with API proxy configured

## Production Configuration

For production, update the CORS configuration to allow your production domain:

```java
config.addAllowedOrigin("https://your-production-domain.com");
```

And build the React app:

```bash
cd resume-tailor-ui
npm run build
```

The built files in the `dist` folder can be served by:
- A separate web server (Nginx, Apache)
- Spring Boot static resources
- Cloud hosting (Azure Static Web Apps, AWS S3 + CloudFront, etc.)
