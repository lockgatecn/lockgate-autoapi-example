package cc.nolink.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auth nolink
 * @create 2021/9/25 20:22
 * @description swagger配置类
 */
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("lockgate auto API")
                .description("自动化接口演示")
                .version("v1.0.0"));
    }

}
