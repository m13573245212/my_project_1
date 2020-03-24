package cn.xlf.workflow;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * 流程定义的挂起和激活
 *
 * @author 类中域:徐林飞
 * @date 2020/1/8 20:46
 */
public class SuspendAndActivateProcessDefinition {
    //挂起全部实例
    @Test
    public void testSuspendAndActivate() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                //通过流程定义的Id进行获取, 得到流程定义的对象
                .processDefinitionId("myProcess_1:2:2504")
                .singleResult();
        boolean suspend = processDefinition.isSuspended();
        if (suspend) {
            //如果暂停
            repositoryService.activateProcessDefinitionById("myProcess_1:2:2504", true, null);
            System.out.println("流程已激活");
        } else {
            repositoryService.suspendProcessDefinitionById("myProcess_1:2:2504", true, null);
            System.out.println("流程已挂起");
        }
    }

    //挂起单个实例
    @Test
    public void testSuspendAndActivateSingle() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //我们不仅可以通过实例Id来访问具体的实例, 还可以使用businessKey来访问携带这种业务标识的流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey("0001")
                .singleResult();
        boolean suspend=processInstance.isSuspended();
        if (suspend){
            runtimeService.activateProcessInstanceById(processInstance.getId());
            System.out.println("流程实例:"+processInstance.getId()+",已激活");
        }else {
            runtimeService.suspendProcessInstanceById(processInstance.getId());
            System.out.println("流程实例:"+processInstance.getId()+",已挂起");
        }
    }
}
