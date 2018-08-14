package cn.thislx.mongodbsimple.demo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: LX
 * @Description:
 * @Date: Created in 16:58 2018/7/23
 * @Modified by:
 */
@Data
@ToString
@Document(collection = "class")
public class Classes implements Serializable {

    @ApiModelProperty(value = "班级ID", name = "classId")
    @Indexed
    private String classId;

    @ApiModelProperty(value = "教师名称", name = "teacherName")
    private String teacherName;

    @ApiModelProperty(value = "学生", name = "students")
    private List<Student> students;
}
