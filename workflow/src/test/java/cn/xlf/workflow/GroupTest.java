package cn.xlf.workflow;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author 类中域:徐林飞
 * @date 2020/1/13 15:14
 */
public class GroupTest {
    @Test
    public void testGroup(){
        ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();
        List<Task> list=processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee("group1")
//                .taskCandidateUser("group1")
                .list();
        list.forEach((task)->{
            System.out.println("task:"+task.getId()+","+task.getName());
            System.out.println("taskAssignee:"+task.getAssignee());
            processEngine.getTaskService().setAssignee(task.getId(),null);

        });
    }
    @Test
    public void test(){
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSXXX");
        System.out.println(simpleDateFormat.format(date));
    }
}
