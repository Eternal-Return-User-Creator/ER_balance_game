package nyj001012.er_bal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // 모든 경로에 대해 모든 메소드를 허용
                registry.addMapping("/api/**")
                        .allowedMethods("*")
                        .allowedOrigins("http://localhost:5173").
                        allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
