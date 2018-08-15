package com.xiaogch;

import com.xiaogch.rpc.RpcServiceScanApplicationListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationListener;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class ,
		MybatisAutoConfiguration.class , TransactionAutoConfiguration.class})
//@ImportResource("classpath:spring/*.xml")
public class Application {

	static Logger LOGGER = LogManager.getLogger(Application.class);
	public static void main(String[] args) {
//		SpringApplication application = new SpringApplication();
//		application.setBannerMode(Banner.Mode.OFF);
//		application.run(args);
//		SpringApplication.run(Application.class, args);

		new SpringApplicationBuilder().sources(Application.class)
				.listeners(new ApplicationListener[]{
						// 注册rpc服务
						new RpcServiceScanApplicationListener()
				})
				.run(args);
	}

}
