package cn.xlf.workflow.controller;

import cn.xlf.workflow.dao.StudentsDao;
import cn.xlf.workflow.entity.StudentsEntity;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 类中域:徐林飞
 * @date 2020/3/9 14:49
 */
@RestController
public class TestHController {
    @Bean
    public TaskRuntimeEventListener taskAssignedListener() {
        return taskAssigned
                -> System.out.println(
                ">>> Task Assigned: '"
                        + taskAssigned.getEntity()
                        +"' We can send a notification to the assignee: "
                        + taskAssigned.getEntity());
    }
    @Autowired
    private TaskRuntime taskRuntime;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private StudentsDao studentsDao;

    @RequestMapping("/testHibernate")
    public String getH() {
        StudentsEntity s = new StudentsEntity();
        s.setId("2");
        s.setName("xiaohong");
        s.setSex("女");
        s.setAddress("haha");
        studentsDao.save(s);
        return "成功";
    }

    @RequestMapping("/testActivitiAndSpringboot")
    public String getActiviti() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        System.out.println("XXXXXXXXXXXXXXXXXX:" + repositoryService);
        return "111";
    }

    @RequestMapping("/testTaskRuntime")
    public void testTaskRuntime() {
        taskRuntime.create(
            TaskPayloadBuilder.create()
                .withName("First Team Task")
                    .withDescription("This is something really important")
                    .withGroup("activitiTeam")
                    .withPriority(10)
                    .build()
        );
    }
}
