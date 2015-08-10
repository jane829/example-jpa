package org.github.jane829.students;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class);
    }

}
