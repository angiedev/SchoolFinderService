package org.angiedev.schoolfinder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

//@SpringBootApplication No longer using because need to exclude CommandLine Runners
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CommandLineRunner.class))
@EnableAutoConfiguration
public class SchoolFinderApplication {
	 public static void main(String[] args) {
	        SpringApplication.run(SchoolFinderApplication.class, args);
	    }
}
