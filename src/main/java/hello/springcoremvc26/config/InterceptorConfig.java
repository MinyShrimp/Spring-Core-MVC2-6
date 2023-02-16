package hello.springcoremvc26.config;

import hello.springcoremvc26.web.interceptor.LogInterceptor;
import hello.springcoremvc26.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**") // 모두 허용
                .excludePathPatterns("/css/**", "/*.ico", "/error"); // BlackList

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/", "/members/css", "/login",
                        "/logout", "/css/**", "*.ico", "/error"
                );
    }
}
