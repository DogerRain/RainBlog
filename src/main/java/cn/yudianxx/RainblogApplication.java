package cn.yudianxx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.yudianxx.system.mapper")
public class RainblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(RainblogApplication.class, args);
    }
}
