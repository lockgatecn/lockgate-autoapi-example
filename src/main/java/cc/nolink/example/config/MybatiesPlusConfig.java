package cc.nolink.example.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"cc.nolink.example.mapper"})
public class MybatiesPlusConfig {

}
