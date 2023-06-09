package com.tibame.myweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//配置自動掃描元件註冊到Spring Container
@SpringBootApplication
@ComponentScan(basePackages= {"com.tibame.controller","com.tibame.component"
		,"com.tibame.service","com.khh.entity"})
@EntityScan(basePackages={"com.tibame.entity","com.khh.entity"})
@EnableJpaRepositories(basePackages={"com.tibame.component","com.khh.entity"})
public class MywebApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(MywebApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(MywebApplication.class);
	}
	
}
 