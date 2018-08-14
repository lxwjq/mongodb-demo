package cn.thislx.mongosource.dao;


import cn.thislx.mongosource.entity.PrimaryMongoObject;
import cn.thislx.mongosource.entity.SecondaryMongoObject;

public interface SecondMongoObjectDao {
    void savePrimary(PrimaryMongoObject primaryMongoObject);

    void saveSecondary(SecondaryMongoObject secondaryMongoObject);

    long getCount(String value);
}
