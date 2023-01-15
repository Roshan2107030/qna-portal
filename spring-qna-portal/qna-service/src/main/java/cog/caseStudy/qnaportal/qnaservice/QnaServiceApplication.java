package cog.caseStudy.qnaportal.qnaservice;

import cog.caseStudy.qnaportal.qnaservice.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class QnaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QnaServiceApplication.class, args);
	}

}
