package com.dsinnovators.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TodoInSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoInSpringApplication.class, args);
	}

}
