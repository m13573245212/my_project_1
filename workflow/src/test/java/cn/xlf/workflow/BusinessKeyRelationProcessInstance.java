package cn.xlf.workflow;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * 通过BusinessKey可以实现我们业务与Activiti框架的融合
 * @author 类中域:徐林飞
 * @date 2020/1/8 20:14
 */
public class BusinessKeyRelationProcessInstance {
    @Test
    public void testBusinessKeyRelationProcessInstance(){
        ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();
        //通过指定流程部署的key 和 businesskey 关联, 我们可以创建一个具有businesskey的流程实例, 进而实现Activiti的业务应用
        ProcessInstance processInstance=processEngine.getRuntimeService().startProcessInstanceByKey("myProcess_1","0001");
        //我们可以通过流程实例来重新获取到businesskey
        System.out.println(processInstance.getBusinessKey());
    }
}
