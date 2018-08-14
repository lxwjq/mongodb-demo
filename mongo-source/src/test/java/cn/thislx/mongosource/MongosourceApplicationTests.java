package cn.thislx.mongosource;

import cn.thislx.mongosource.dao.SecondMongoObjectDao;
import cn.thislx.mongosource.entity.PrimaryMongoObject;
import cn.thislx.mongosource.entity.SecondaryMongoObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongosourceApplicationTests {

	@Autowired
	private SecondMongoObjectDao secondMongoObjectDao;

	@Test
	public void testSavePrimary() throws Exception {
		PrimaryMongoObject primaryMongoObject = new PrimaryMongoObject();
		primaryMongoObject.setId("p1" + new Date().getTime());
		primaryMongoObject.setValue("xiaoming1");
		secondMongoObjectDao.savePrimary(primaryMongoObject);
	}

	@Test
	public void testSaveSecondary() {
		SecondaryMongoObject secondaryMongoObject = new SecondaryMongoObject();
		secondaryMongoObject.setId("s1" + new Date().getTime());
		secondaryMongoObject.setValue("xiaoming1");
		secondMongoObjectDao.saveSecondary(secondaryMongoObject);
	}

	@Test
	public void testGetCount() {

		long count = secondMongoObjectDao.getCount("xiaoming");
		System.out.println("===============================count:" + count);
	}


}
