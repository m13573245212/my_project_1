package cn.xlf.workflow;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 并行网关测试
 * @author 类中域:徐林飞
 * @date 2020/3/9 20:07
 */
public class ParallelTest {
    @Test
    public void testParallel(){
        ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService=processEngine.getRepositoryService();
        repositoryService.createDeployment()
                .addClasspathResource("diagram/MyProcess_parallel.png")
                .addClasspathResource("diagram/MyProcess_parallel.bpmn")
                .key("parallel_test")
                .name("并行网关测试")
                .deploy();
        System.out.println("-----------部署完成------------");
    }

    //测试并行流程的执行情况
    @Test
    public void testParallelStart(){
        ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
        Map<String,Object> map=new HashMap<>();
        map.put("user","xu");
        ProcessInstance processInstance=processEngine.getRuntimeService().startProcessInstanceById("myProcess:4:87504",map);
        System.out.println(processInstance.getId());
        System.out.println(processInstance.getName());
    }
    //执行
    @Test
    public void testTask(){
        ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();

        List<Task> list=processEngine.getTaskService().createTaskQuery().taskCandidateOrAssigned("xu").list();
        list.forEach((t)->{
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println(t.getId());
        });
        System.out.println("遍历完成,开始执行:");

        processEngine.getTaskService().setVariableLocal("92506","user","lin");
        processEngine.getTaskService().complete("92506");
        System.out.println("执行完毕");
    }
}
