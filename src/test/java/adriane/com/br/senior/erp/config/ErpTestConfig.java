package adriane.com.br.senior.erp.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErpTestConfig {
    @Bean
    Faker faker() {
        return Faker.instance();
    }
}
