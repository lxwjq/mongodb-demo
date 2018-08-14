package cn.thislx.mongosource.dao;

import cn.thislx.mongosource.entity.PrimaryMongoObject;
import cn.thislx.mongosource.entity.SecondaryMongoObject;
import cn.thislx.mongosource.repository.primary.PrimaryRepository;
import cn.thislx.mongosource.repository.secondary.SecondaryRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 业务实现
 * 
 * @date 2017年10月14日
 *
 */
@Service
public class SecondMongoObjectDaoImpl implements SecondMongoObjectDao {

	@Resource
	private PrimaryRepository primaryRepository;
	@Resource
	private SecondaryRepository secondaryRepository;

	@Override
	public void savePrimary(PrimaryMongoObject primaryMongoObject) {
		primaryRepository.save(primaryMongoObject);
	}

	@Override
	public void saveSecondary(SecondaryMongoObject secondaryMongoObject) {
		secondaryRepository.save(secondaryMongoObject);
	}

	@Override
	public long getCount(String value) {
		long primary = primaryRepository.findAll().size();
		long secondary = secondaryRepository.findAll().size();
		return (primary + secondary);
	}

}
