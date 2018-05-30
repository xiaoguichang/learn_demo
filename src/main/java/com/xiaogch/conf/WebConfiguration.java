package com.xiaogch.conf;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.xiaogch.common.http.interceptor.HttpRequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.config.AnnotationDrivenBeanDefinitionParser;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/4 0004.
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    static Logger logger = LoggerFactory.getLogger(WebConfiguration.class);

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(new HttpRequestInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/druid/**");
    }

    /**
     * {@inheritDoc}
     * <p>This implementation is empty.
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        AnnotationDrivenBeanDefinitionParser a;
    }

    @Bean
    public FastJsonConfig fastJsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        return fastJsonConfig;
    }

    /**
     * 转换Http请求和响应，FastJson
     * @param fastJsonConfig
     * @return
     */
    @Bean
    @Autowired
    public HttpMessageConverters fastJsonHttpMessageConverters(FastJsonConfig fastJsonConfig) {
        logger.info("fastJsonHttpMessageConverters, init HttpMessageConverters ... ");
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.ALL);
        fastConverter.setSupportedMediaTypes(mediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }

    /**
     * {@inheritDoc}
     * <p>This implementation is empty.
     * 跨域请求
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/cross/**");
    }
}
