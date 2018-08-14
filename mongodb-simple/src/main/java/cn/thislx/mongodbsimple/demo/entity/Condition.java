package cn.thislx.mongodbsimple.demo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: LX
 * @Description:
 * @Date: Created in 22:15 2018/7/23
 * @Modified by:
 */
@Data
@ToString
public class Condition implements Serializable{

    @ApiModelProperty(value = "查询条件的班级ID", name = "classId")
    private String classId;

    @ApiModelProperty(value = "教师名称", name = "teacherName")
    private String teacherName;

    @ApiModelProperty(value = "查询条件的学生对象", name = "student")
    private Student student;
}
