package cn.thislx.mongodbsimple.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @Author: LX
 * @Description: 学生实体类用于mongodb操作，Document注解表明插入的集合名称
 * @Date: Created in 16:27 2018/7/23
 * @Modified by:
 */
@Data
@ToString
@Document(collection = "class")
public class Student implements Serializable {

    @ApiModelProperty(value = "学生编号", name = "studentId")
    private String studentId;

    @ApiModelProperty(value = "学生姓名", name = "name")
    private String name;

    @ApiModelProperty(value = "学生年龄", name = "age")
    private Integer age;

    /**
     * 学生性别   男生：0 女生 1
     */
    @ApiModelProperty(value = "学生性别", name = "sex")
    private Integer sex;

    /**
     * 限制参数时间格式
     */
    @ApiModelProperty(value = "学生生日", name = "birthday")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String birthday;
}
