package cn.thislx.mongosource.repository.primary;

import cn.thislx.mongosource.entity.PrimaryMongoObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrimaryRepository extends MongoRepository<PrimaryMongoObject, String> {
}
