package cc.nolink.example;

import cn.hutool.core.net.NetUtil;
import cn.lockgate.framework.datasource.register.MultiDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @auth reset
 * @description
 * @since 2022/9/20 19:17
 */
@EnableTransactionManagement
@Import({MultiDataSourceRegister.class})
@SpringBootApplication
public class ExampleApplication {

    public static void main(String[] args) {
        // 设置系统参数：ip，为了输出到日志${sys:ip}
        System.setProperty("ip", NetUtil.getLocalhost().getHostAddress());
        SpringApplication.run(ExampleApplication.class, args);
    }

}
