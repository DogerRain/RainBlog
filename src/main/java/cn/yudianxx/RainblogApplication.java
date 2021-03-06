package cn.yudianxx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("cn.yudianxx.system.mapper")
@EnableAsync
public class RainblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(RainblogApplication.class, args);
    }
}
