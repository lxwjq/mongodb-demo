package cn.thislx.mongosource.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


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
