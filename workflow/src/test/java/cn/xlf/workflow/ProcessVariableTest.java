package cn.xlf.workflow;

import cn.xlf.workflow.process.Holiday;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author 类中域:徐林飞
 * @date 2020/1/11 10:50
 */
public class ProcessVariableTest {
    @Test
    public void processVariableTest1() {
        System.out.println("============开始流程部署===========");
        ProcessEngine processEngine = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("activiti.cfg.xml", "standaloneProcessEngineConfiguration")
                .buildProcessEngine();
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("diagram/holiday_group.bpmn")
                .addClasspathResource("diagram/holiday_group.png")
                .name("流程变量部署测试")
                .key("variableTest")
                .deploy();
        System.out.println("=============部署完成, 部署ID:" + deployment.getId() + "===============");
    }

    @Test
    public void testProcessInstance() {
        System.out.println("=============开始流程实例的创建=============");
        ProcessEngine processEngine = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("activiti.cfg.xml")
                .buildProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> map = new HashMap<>();
        Holiday holiday = new Holiday()
                .setId(1)
                .setBeginTime(LocalDateTime.now())
                .setEndTime(LocalDateTime.now().plusDays(3))
                .setNum(3)
                .setReason("就想请假")
                .setType("找抽型");
        map.put("holiday", holiday);
        map.put("user1", "我");
        map.put("user2", "还是我");
        map.put("user3", "爸爸");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("myProcess_group:1:65004");
        System.out.println("=============流程实例创建完成: =============");
        System.out.println("实例ID:" + processInstance.getId() + ";\n"
                + "流程定义ID:" + processInstance.getProcessDefinitionId() + ";\n");
        System.out.println("==========================================");
    }

    //进行流程变量的后续添加
    @Test
    public void testAddAfterCreateProcessInstance(){
        ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
        //设置一个变量
        processEngine.getRuntimeService().setVariable("62501","user2","新增的用户");
        //设置一组变量
//        Map<String,Holiday> map=new HashMap<>();
//        Holiday holiday=new Holiday().setNum(5);
//        map.put("holiday",holiday);
//        processEngine.getRuntimeService().setVariables("62501",map);
    }

    /**
     * 测试获取taskId
     */
    @Test
    public void getId() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Task task=processEngine.getTaskService()
                .createTaskQuery()
                .processInstanceId("62501")
                .active()
                .singleResult();
        System.out.println("task:"+task.getId());
        System.out.println("task:"+task.getName());

        processEngine.getTaskService().setVariableLocal(task.getId(),"user2","新增的用户");
        Map<String,Holiday> map = new HashMap<>();
        Holiday holiday = new Holiday();

        map.put("holiday", holiday);
        processEngine.getTaskService().setVariablesLocal(task.getId(),map);

    }

    /**
     * 测试流程是否按照变量的控制进行
     */
    @Test
    public void testProcess() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        //获取流程实例的节点,并让这个流程走完
        BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel("myProcess:2:32504");
        Collection<FlowElement> collection = bpmnModel.getMainProcess().getFlowElements();
        collection.forEach((flowElement) -> {
            System.out.println(flowElement.getName());
        });
        /**
         * 去除开始节点和最后节点, 这两个不需要手动执行,还要去掉name为null的节点, 它们可能是线条节点,collection本身不存在是否有序
         * 的问题, 但我们在此只需要知道它的元素数量
         */
        int len=collection.size()-2;
        Iterator<FlowElement> iterator=collection.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getName()==null){
                iterator.remove();
                len--;
            }
        }
        System.out.println(len);
        for(int i=0;i<len;i++){
            List<Task> list = taskService.createTaskQuery().active().processDefinitionId("myProcess:2:32504").processInstanceId("55001").list();
            list.forEach((task) -> {
                System.out.println("============1个节点开始查询============");
                System.out.println("流程的ID:" + task.getId());
                System.out.println("流程的名称:" + task.getName());
                System.out.println("流程的执行者:" + task.getAssignee());
                System.out.println("============1个节点结束查询============");
                System.out.println("++++++++++++1个节点开始执行++++++++++++");
                taskService.complete(task.getId());
                System.out.println(task.getName() + "节点执行完毕");
                System.out.println("++++++++++++1个节点结束执行++++++++++++");
            });
        }
    }
}
