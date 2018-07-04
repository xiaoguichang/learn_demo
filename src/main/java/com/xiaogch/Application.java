package com.xiaogch;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class ,
		MybatisAutoConfiguration.class , TransactionAutoConfiguration.class})
@ImportResource("classpath:spring/*.xml")
public class Application {

	public static void main(String[] args) {
		org.springframework.boot.actuate.endpoint.web.servlet.AbstractWebMvcEndpointHandlerMapping a;
//		SpringApplication application = new SpringApplication();
//		application.setBannerMode(Banner.Mode.OFF);
//		application.run(args);
//		DispatcherServlet dispatcherServlet;
//		new SpringApplicationBuilder().sources(Application.class)
//				.listeners(new ApplicationListener<ApplicationEvent>() {
//					@Override
//					public void onApplicationEvent(ApplicationEvent event) {
//
//					}
//				})
//				.run(args);
		SpringApplication.run(Application.class, args);
	}
}
