package br.com.builders;

import br.com.builders.infra.mongo.config.MongodbConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(MongodbConfig.class)
@SpringBootApplication
public class BuildersApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuildersApplication.class, args);
	}

}
