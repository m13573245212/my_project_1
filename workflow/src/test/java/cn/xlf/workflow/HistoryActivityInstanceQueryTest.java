package cn.xlf.workflow;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.junit.Test;

import java.util.List;

/**
 * 进行流程历史信息的查看
 *
 * @author 类中域:徐林飞
 * @date 2020/1/8 16:08
 */
public class HistoryActivityInstanceQueryTest {
    /**
     * 进行流程实例历史查看
     */
    @Test
    public void testHistorySelect() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> list=historyService.createHistoricActivityInstanceQuery()
                //通过流程实例的ID进行查询
                .processInstanceId("10001")
                //按照节点开始时间进行排序
                .orderByHistoricActivityInstanceStartTime().asc()
                .list();

        //遍历
        list.forEach((historicActivityInstance)->{
            System.out.println(historicActivityInstance.getActivityId());
            System.out.println(historicActivityInstance.getActivityName());
            System.out.println(historicActivityInstance.getProcessDefinitionId());
            System.out.println(historicActivityInstance.getProcessInstanceId());
            System.out.println(historicActivityInstance.getTaskId());
            System.out.println("=========================================");
        });
    }
}
