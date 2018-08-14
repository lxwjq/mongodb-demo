package cn.thislx.mongodbsimple.demo.controller;

import cn.thislx.mongodbsimple.demo.entity.*;
import com.alibaba.fastjson.JSON;
import com.mongodb.WriteResult;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: LX
 * @Description: 使用mongoTemplate进行常用数据操作
 * @Date: Created in 16:18 2018/7/23
 * @Modified by:
 */
@RestController
public class MongodbController {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * @param student: 保存的对象
     * @Author: LX
     * @Description: mongodb进行数据的单一保存
     * @Date: 2018/7/23 16:56
     * @Modified by:
     */
    @ApiOperation(value = "单一保存", notes = "单一保存对象")
    @PostMapping("/singleSave")
    public Student singleSave(@RequestBody Student student) {
        mongoTemplate.insert(student);
        return student;
    }

    /**
     * @param classes: 保存的对象
     * @Author: LX
     * @Description: 内嵌保存数据，eg:保存一个班级和班级中的学生集合
     * @Date: 2018/7/25 13:21
     * @Modified by:
     */
    @ApiOperation(value = "嵌套保存", notes = "嵌套保存对象")
    @PostMapping("/nestSave")
    public Classes nestSave(@RequestBody Classes classes) {
        //使用fastJson将对象转化为Json
        String json = JSON.toJSONString(classes);
        mongoTemplate.insert(json, "class");
        return classes;
    }


    /**
     * @param classId: 班级Id
     * @Author: LX
     * @Description: 根据单条件进行删除
     * @Date: 2018/7/25 13:26
     * @Modified by:
     */
    @ApiOperation(value = "简单删除", notes = "简单删除")
    @PostMapping("/singleDelete")
    public Map<String, String> singleDelete(@RequestBody String classId) {
        Map<String, String> map = new ConcurrentHashMap<String, String>();
        try {
            Query query = new Query(Criteria.where("classId").is(classId));
            WriteResult aClass = mongoTemplate.remove(query, "class");
            map.put("result", "success");
//            map.put("msg", "删除成功共" + aClass.getDeletedCount() + "条数据");
        } catch (Exception e) {
            map.put("result", "failed");
            map.put("msg", "删除失败");
            e.printStackTrace();
        }
        return map;
    }


    /**
     * @param condition:嵌套删除数据的参数
     * @Author: LX
     * @Description: 使用upsert进行嵌套删除这种方法有一个缺陷，会将符合条件的数据修改成null，而不会进行实际删除
     * @Date: 2018/7/24 18:06
     * @Modified by:
     */
    @ApiOperation(value = "upsert删除嵌套数据", notes = "删除嵌套数据")
    @PostMapping("/upsertNestDelete")
    public Map<String, String> upsertNestDelete(@RequestBody Condition condition) {
        Map<String, String> map = new ConcurrentHashMap<String, String>();
        try {
            Query query = Query.query(Criteria.where("classId").is(condition.getClassId()).
                    and("students.studentId").is(condition.getStudent().getStudentId()));
            Update update = new Update();
            update.unset("students.$");
            WriteResult aClass = mongoTemplate.upsert(query, update, "class");
            map.put("result", "success");
//            map.put("msg", "嵌套删除成功共" + aClass.getMatchedCount() + "条数据");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "failed");
            map.put("msg", "嵌套删除失败");
        }
        return map;
    }


    /**
     * @param condition:嵌套删除数据的参数
     * @Author: LX
     * @Description: 如果要彻底删除，则需要使用pull(pull删除内嵌文档的时候，student对象的值一定要和被删除的一模一样)
     * @Date: 2018/7/24 18:06
     * @Modified by:
     */
    @ApiOperation(value = "pull删除嵌套数据", notes = "删除嵌套数据")
    @PostMapping("/pullNestDelete")
    public Map<String, String> pullNestDelete(@RequestBody Condition condition) {
        Map<String, String> map = new ConcurrentHashMap<String, String>();
        try {
            Query query = Query.query(Criteria.where("classId").is(condition.getClassId()));
            Update update = new Update();
            update.pull("students", condition.getStudent());
            WriteResult aClass = mongoTemplate.upsert(query, update, "class");
            map.put("result", "success");
//            map.put("msg", "pull删除嵌套数据" + aClass.getMatchedCount() + "条数据");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "failed");
            map.put("msg", "嵌套删除失败");
        }
        return map;
    }

    /**
     * @param condition:查询对象的封装
     * @Author: LX
     * @Description:进行单条件查询
     * @Date: 2018/7/25 13:23
     * @Modified by:
     */
//    @ApiOperation(value = "条件查询", notes = "条件查询")
//    @PostMapping("/conditionFind")
//    public List<Classes> conditionFind(@RequestBody Condition condition) {
//        Query query = new Query(Criteria.where("classId").is(condition.getClassId()));
//        List<Classes> aClass = mongoTemplate.find(query, Classes.class, "class");
//        return aClass;
//    }


    /**
     * @param condition: 查询内嵌对象参数封装
     * @Author: LX
     * @Description:内嵌条件查询 eg:查询101班级名字叫lisi的学生
     * @Date: 2018/7/25 13:24
     * @Modified by:
     */
    @ApiOperation(value = "内嵌条件查询", notes = "嵌套条件查询")
    @PostMapping("/nestConditionFind")
    public List<Classes> nestConditionFind(@RequestBody Condition condition) {
        Query query = new Query(Criteria.where("classId").is(condition.getClassId()).
                and("students.name").is(condition.getStudent().getName()));
        List<Classes> aClass = mongoTemplate.find(query, Classes.class, "class");
        return aClass;
    }

    /**
     * @param classId:班级ID
     * @Author: LX
     * @Description:进行单条件查询
     * @Date: 2018/7/25 13:23
     * @Modified by:
     */
    @ApiOperation(value = "单条件查询", notes = "单条件查询")
    @PostMapping("/findById")
    public Map<String, Object> findById(@RequestBody String classId) {
        Map<String, Object> map = new ConcurrentHashMap<String, Object>();
        try {
            Query query = Query.query(Criteria.where("classId").is(classId));
            List<Classes> aClass = mongoTemplate.find(query, Classes.class, "class");
            map.put("result", "success");
            map.put("data", aClass);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "failed");
        }
        return map;
    }

    /**
     * @param condition:分页对象查询参数封装
     * @Author: LX
     * @Description: 分页查询
     * @Date: 2018/7/25 13:29
     * @Modified by:
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/findClassPage")
    public Map<String, Object> findClassPage(@RequestBody PageCondition<Condition> condition) {
        Map<String, Object> map = new ConcurrentHashMap<String, Object>();
        Criteria criteria = null;
        try {
            //对条件进行非空判断  类似于mybatis中的<if test=""></if>
            if (StringUtils.isNotEmpty(condition.getT().getClassId())) {
                criteria = Criteria.where("classId").is(condition.getT().getClassId());
            } else {
                criteria = Criteria.where("classId").ne(null);
            }

            if (StringUtils.isNotEmpty(condition.getT().getStudent().getName())) {
                criteria.and("student.name").is(condition.getT().getStudent().getName());
            }

            if (StringUtils.isNotEmpty(condition.getT().getStudent().getStudentId())) {
                criteria.and("student.studentId").is(condition.getT().getStudent().getStudentId());
            }
            Query query = Query.query(criteria);
            //创建分页对象
            Pageable pageable = new PageRequest(condition.getCurrentPageNo(), condition.getPageSize());
            query.with(pageable);

            // 排序
            query.with(new Sort(Sort.Direction.ASC, "age"));

            // 查询总数
            int count = (int) mongoTemplate.count(query, Classes.class, "class");
            List<Classes> items = mongoTemplate.find(query, Classes.class, "class");
            Page<List<Classes>> listPage = new Page<>();
            listPage.setCurrentPageNo(condition.getCurrentPageNo());
            listPage.setPageSize(condition.getPageSize());
            listPage.setTotalRows(count);
            listPage.setT(items);
            map.put("data", listPage);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * @param condition: 修改对象
     * @Author: LX
     * @Description: mongodb进行简单的修改  介绍upsert、updateFirst、updateMulti区别
     * @Date: 2018/7/25 13:02
     * @Modified by:
     */
    @PostMapping("/singleUpdate")
    @ApiOperation(value = "简单修改", notes = "简单修改数据")
    public Map<String, Object> singleUpdate(@RequestBody Condition condition) {
        Map<String, Object> map = new ConcurrentHashMap<String, Object>();
        try {
            Query query = Query.query(Criteria.where("classId").is(condition.getClassId()));

            //1、如果不存在修改的此列  mongodb会进行自动创建  没有查询到数据  mongodb也会自动新增一条数据
            Update update = Update.update("teacherName", condition.getTeacherName());
            WriteResult result = mongoTemplate.upsert(query, update, "class");

            //2、更新满足条件的第一条数据  没有查询到数据不会增加新数据
            //mongoTemplate.updateFirst(query, update, "class");

            //3、更新所有满足条件的数据   没有查询到数据不会增加新数据
            //mongoTemplate.updateMulti(query, update, "class");
            map.put("result", "success");
//            map.put("msg", "共简单修改" + result.getMatchedCount() + "条数据");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "failed");
        }
        return map;
    }


    /**
     * @param condition
     * @Author: LX
     * @Description:保存嵌套数据 介绍push、addToSet在保存中的区别
     * @Date: 2018/7/25 13:04
     * @Modified by:
     */
    @PostMapping("/innerSave")
    @ApiOperation(value = "保存嵌套数据", notes = "保存嵌套数据")
    public Map<String, Object> innerSave(@RequestBody Condition condition) {
        Map<String, Object> map = new ConcurrentHashMap<String, Object>();
        try {
            Query query = Query.query(Criteria.where("classId").is(condition.getClassId()));
            Update update = new Update();

            //1、push如果数据已经存在,会插入一条一样的数据。
            //update.push("Students", student);

            //2、addToSet如果数据已经存在，则不做任何操作
            update.addToSet("students", condition.getStudent());
            WriteResult result = mongoTemplate.upsert(query, update, "class");
            map.put("result", "success");
//            map.put("msg", "共简单修改" + result.getMatchedCount() + "条数据");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "failed");
        }
        return map;
    }


    @PostMapping("/innerUpdate")
    @ApiOperation(value = "修改嵌套数据", notes = "修改嵌套数据")
    public Map<String, Object> innerUpdate(@RequestBody Condition condition) {
        Map<String, Object> map = new ConcurrentHashMap<String, Object>();
        try {
            Query query = Query.query(Criteria.where("classId").is(condition.getClassId())
                    .and("students.studentId").is(condition.getStudent().getStudentId()));
            Update update = Update.update("students.$.name", condition.getStudent().getName());
            WriteResult result = mongoTemplate.upsert(query, update, "class");
            map.put("result", "success");
//            map.put("msg", "共简单修改" + result.getMatchedCount() + "条数据");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "failed");

        }
        return map;
    }

}
