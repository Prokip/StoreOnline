package com.online.store.config;

import com.online.store.swagger.SwaggerConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan
@Import(SwaggerConfig.class)
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    public static final String CLASSPATH_META_INF_RESOURCES = "classpath:/META-INF/resources/";
    public static final String CLASSPATH_META_INF_RESOURCES_WEBJARS = "classpath:/META-INF/resources/webjars/";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleChangeInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations(CLASSPATH_META_INF_RESOURCES);

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations(CLASSPATH_META_INF_RESOURCES_WEBJARS);
    }
}
