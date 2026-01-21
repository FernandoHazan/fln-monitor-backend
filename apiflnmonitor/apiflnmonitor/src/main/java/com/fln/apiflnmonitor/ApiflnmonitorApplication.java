package com.fln.apiflnmonitor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiflnmonitorApplication  {
	public static void main(String[] args) {
		SpringApplication.run(ApiflnmonitorApplication.class, args);
	}
}
