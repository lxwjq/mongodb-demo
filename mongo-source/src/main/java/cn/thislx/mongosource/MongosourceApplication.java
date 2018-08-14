package cn.thislx.mongosource;

import cn.thislx.mongosource.config.props.MultipleMongoProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(MultipleMongoProperties.class)
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class MongosourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongosourceApplication.class, args);
	}
}
