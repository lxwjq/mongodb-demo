package cn.thislx.mongosource.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author liangming.deng
 * @date   2017年10月14日
 *
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "second_mongo")
public class SecondaryMongoObject {

	@Id
	private String id;

	private String value;

}
