package cn.thislx.mongodbsimple.demo.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: LX
 * @Description:
 * @Date: Created in 20:09 2018/7/24
 * @Modified by:
 */
@Data
@ToString
public class Page<T> implements Serializable {

    /**
     * 每页显示
     */
    private int pageSize = 20;

    /**
     * 总页数
     */
    private int totalPageNo = 1;

    private int currentPageNo;

    /**
     * 总条数
     */
    private int totalRows = 0;

    private T t;

    public int getTotalPageNo() {
        return this.getTotalRows() % this.getPageSize() == 0 ? this.getTotalRows() / this.getPageSize() : this.getTotalRows() / this.getPageSize() + 1;
    }
}
