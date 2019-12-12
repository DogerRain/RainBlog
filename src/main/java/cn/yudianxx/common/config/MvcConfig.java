package cn.yudianxx.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author huangyongwen
 * @date 2019/12/12
 * @Description
 */

//@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {
//    相当于在yml的配置
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}