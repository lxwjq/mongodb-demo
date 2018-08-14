package cn.thislx.mongosource.repository.secondary;

import cn.thislx.mongosource.entity.SecondaryMongoObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SecondaryRepository extends MongoRepository<SecondaryMongoObject, String> {
}
