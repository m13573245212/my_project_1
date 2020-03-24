package cn.xlf.workflow;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.List;

/**
 * @author 类中域:徐林飞
 * @date 2020/1/7 09:21
 */
@SpringBootTest
public class ProcessDefinitionSelectAndDelete {
    //流程定义的查询
    @Test
    public void testProcessDefinitionQuery() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                //指定key可缩小范围
                .processDefinitionKey("myProcess_1")
                //指定排序方式
                .orderByProcessDefinitionVersion()
                .desc()
                .list();

        list.forEach((p) -> {
            System.out.println("流程的Id:" + p.getId());
            System.out.println("流程的名称:" + p.getName());
            System.out.println("流程的部署Id:" + p.getDeploymentId());
            //这里的key指的是表ACT_RE_PROCDEF中的key,即bpmn文件中的Id
            System.out.println("流程的key:" + p.getKey());
            System.out.println("流程的描述:" + p.getDescription());
            System.out.println("流程的版本号:" + p.getEngineVersion());
            System.out.println("--------------------------");
        });
    }

    //流程定义的删除
    @Test
    public void testDeleteProcessDefinition() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //按照流程部署ID进行删除
        repositoryService.deleteDeployment("25001");
    }

    //流程资源的查看
    @Test
    public void testProcessBpmnAndPng() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<ProcessDefinition> list = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("myProcess_1")
                .list();
        //得到部署Id
        list.forEach((processDefinition) -> {

            System.out.println("---------开始----------");
            String deploymentId = processDefinition.getDeploymentId();
            System.out.println("部署Id:" + deploymentId);
            File bpmnFile=new File("D:/1/"+deploymentId+".bpmn");
            File pngFile=new File("D:/1/"+deploymentId+".png");
            try{
                if(!bpmnFile.exists()){
                    bpmnFile.createNewFile();
                }
                if(!pngFile.exists()){
                    pngFile.createNewFile();
                }
            }catch (IOException e){
                System.out.println("文件创建失败");
                e.printStackTrace();
            }

            try (
                    //通过部署Id获取bpmn和png
                    //bpmn
                    InputStream bpmnInput = repositoryService.getResourceAsStream(deploymentId, processDefinition.getResourceName());
                    //png
                    InputStream pngInput = repositoryService.getResourceAsStream(deploymentId, processDefinition.getDiagramResourceName());

                    OutputStream bpmnOutput = new FileOutputStream(bpmnFile);
                    OutputStream pngOutput = new FileOutputStream(pngFile)
            ) {
                byte[] bytes1 = new byte[1024];
                byte[] bytes2 = new byte[1024];
                int i1, i2;
                while ((i1 = bpmnInput.read(bytes1)) != -1) {
                    bpmnOutput.write(bytes1, 0, i1);
                }
                while ((i2 = pngInput.read(bytes2)) != -1) {
                    pngOutput.write(bytes2, 0, i2);
                }
            } catch (IOException e) {
                System.out.println("文件复制失败");
                e.printStackTrace();
            }
            System.out.println("---------结束----------");
        });
    }

}
