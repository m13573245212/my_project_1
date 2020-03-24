package cn.xlf.workflow;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用UEL表达式来动态指定任务的处理人
 * @author 类中域:徐林飞
 * @date 2020/1/8 22:05
 */
public class AssigneeUEL {
    @Test
    public void testUELAssign(){
        //部署带UEL表达式的流程图
        ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService=processEngine.getRepositoryService();
        repositoryService.createDeployment()
                .addClasspathResource("diagram/fileupload.png")
                .addClasspathResource("diagram/fileupload.bpmn")
                .key("uel_test")
                .name("测试任务指定")
                .deploy();
        System.out.println("-----------部署完成------------");
    }

    //为流程图中的变量进行赋值
    @Test
    public void testUelSetValue(){
        ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService=processEngine.getRuntimeService();

        Map<String,Object> map=new HashMap<>();
        map.put("user1","我");
        map.put("user2","爸爸");
        ProcessInstance processInstance=runtimeService.startProcessInstanceById("myProcess:1:27504",map);
        System.out.println("流程实例Id:"+processInstance.getId());
    }

}
