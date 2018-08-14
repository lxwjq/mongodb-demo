package cn.thislx.mongosource.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author 
 */
@Data
@ToString
@AllArgsConstructor  // 自动生成全参数构造函数。
@NoArgsConstructor  //自动生成无参数构造函数。
@Document(collection = "first_mongo")
public class PrimaryMongoObject {

	@Id
	private String id;

	private String value;
}
