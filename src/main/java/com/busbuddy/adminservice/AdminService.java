package com.busbuddy.adminservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan(basePackages = "com.busbuddy.adminservice")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class AdminService {

	public static void main(String[] args) {
		SpringApplication.run(AdminService.class, args);
	}

}
