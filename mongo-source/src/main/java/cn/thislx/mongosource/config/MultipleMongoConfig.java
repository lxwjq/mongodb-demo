package cn.thislx.mongosource.config;

import cn.thislx.mongosource.config.props.MultipleMongoProperties;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取各数据源MongoTemplate
 *
 */
@Configuration
public class MultipleMongoConfig {

	@Autowired
	private MultipleMongoProperties mongoProperties;

	@Primary
	@Bean
	@Qualifier(PrimaryMongoConfig.MONGO_TEMPLATE)
	public MongoTemplate primaryMongoTemplate() throws Exception {
		return new MongoTemplate(primaryFactory(this.mongoProperties.getPrimary()));
	}

	@Bean
	@Qualifier(SecondaryMongoConfig.MONGO_TEMPLATE)
	public MongoTemplate secondaryMongoTemplate() throws Exception {
		return new MongoTemplate(secondaryFactory(this.mongoProperties.getSecondary()));
	}

	@Bean
	@Primary
	public MongoDbFactory primaryFactory(MongoProperties mongo) throws Exception {
		ServerAddress serverAddress = new ServerAddress(mongo.getHost(), mongo.getPort());
		List<MongoCredential> mongoCredentialList = new ArrayList<>();
		mongoCredentialList
				.add(MongoCredential.createCredential(mongo.getUsername(), mongo.getDatabase(), mongo.getPassword()));
		return new SimpleMongoDbFactory(new MongoClient(serverAddress, mongoCredentialList), mongo.getDatabase());
	}

	@Bean
	public MongoDbFactory secondaryFactory(MongoProperties mongo) throws Exception {
		ServerAddress serverAddress = new ServerAddress(mongo.getHost(), mongo.getPort());
		List<MongoCredential> mongoCredentialList = new ArrayList<>();
		mongoCredentialList
				.add(MongoCredential.createCredential(mongo.getUsername(), mongo.getDatabase(), mongo.getPassword()));
		return new SimpleMongoDbFactory(new MongoClient(serverAddress, mongoCredentialList), mongo.getDatabase());
	}
}
