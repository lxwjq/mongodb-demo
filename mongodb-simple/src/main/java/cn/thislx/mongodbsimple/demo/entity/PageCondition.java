package cn.thislx.mongodbsimple.demo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: LX
 * @Description:
 * @Date: Created in 20:02 2018/7/24
 * @Modified by:
 */
@Data
@ToString
public class PageCondition<T> {

    @ApiModelProperty(value = "查询对象", name = "t")
    private T t;

    @ApiModelProperty(value = "页码", name = "pageNo")
    private int currentPageNo;

    @ApiModelProperty(value = "每页显示", name = "pageSize")
    private int pageSize;

}
