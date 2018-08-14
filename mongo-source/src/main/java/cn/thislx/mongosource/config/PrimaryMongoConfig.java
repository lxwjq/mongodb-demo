package cn.thislx.mongosource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author 
 * 主数据源 数据层
 */
@Configuration
@EnableMongoRepositories(basePackages = "cn.thislx.mongosource.repository.primary",
		mongoTemplateRef = PrimaryMongoConfig.MONGO_TEMPLATE)
public class PrimaryMongoConfig {

	protected static final String MONGO_TEMPLATE = "primaryMongoTemplate";
}
