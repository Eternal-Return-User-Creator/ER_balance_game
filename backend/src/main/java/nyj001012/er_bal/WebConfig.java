package nyj001012.er_bal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로에 대해 모든 메소드를 허용
        registry.addMapping("/api/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 필요한 HTTP 메서드 허용
                .allowedOrigins("http://localhost:5173", "http://localhost:3000")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
