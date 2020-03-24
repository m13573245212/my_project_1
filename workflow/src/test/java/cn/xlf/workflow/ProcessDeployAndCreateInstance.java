package cn.xlf.workflow;

import cn.xlf.workflow.process.Holiday;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;

import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

public class ProcessDeployAndCreateInstance {

    /**
     * 使用默认的ProcessEngine创建方式, 使用该方式是不可以更改activiti.cfg.xml的名称
     * 也不能更改内部processEngineConfiguration的bean的id
     */
    @Test
    public void testActivitiCreateDefault() {
        //1.创建ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(processEngine);
        //2.得到RepositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3.进行部署
        Deployment deployment = repositoryService.createDeployment()//创建Deployment对象
                .addClasspathResource("diagram/holiday-exclusive.bpmn")//添加bpmn文件
                .addClasspathResource("diagram/holiday-exclusive.png")//添加png文件
                .name("排他网关任务测试")
                .key("holiday-exclusive")
                .deploy();//部署

        //4.输出部署的一些信息
        System.out.println(deployment.getName());
        System.out.println(deployment.getId());
    }
    /**
     * ProcessEngine的创建方式二
     * 该方式可以指定配置文件的名称;
     * processEngineConfiguration的bean可以改名;
     */
    @Test
    public void testProcessEngine(){
        ProcessEngineConfiguration processEngineConfiguration=
                ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml",
                        "processEngineConfiguration");
        ProcessEngine processEngine=processEngineConfiguration.buildProcessEngine();
        System.out.println(processEngine);
    }

    /**
     * 创建流程实例
     */
    @Test
    public void testProcessInstance(){
        ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService=processEngine.getRuntimeService();
        ProcessInstance processInstance=runtimeService.startProcessInstanceById("myProcess:3:75004");
        System.out.println("流程部署ID:"+processInstance.getDeploymentId());
        System.out.println("流程定义ID:"+processInstance.getProcessDefinitionId());
        System.out.println("流程实例ID:"+processInstance.getId());
        System.out.println("流程活动ID:"+processInstance.getActivityId());
    }
    /**
     * 查询当前用户的任务列表
     */
    @Test
    public void testTaskByUser(){
        ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
        TaskService taskService=processEngine.getTaskService();
//        List<Task> taskList=taskService.createTaskQuery()
//                .processDefinitionKey("myProcess_1")
//                .taskAssignee("徐林飞")
//                .list();
//        for (Task task: taskList) {
//            System.out.println("流程实例的ID:"+task.getProcessInstanceId());
//            System.out.println("任务ID:"+task.getId());
//            System.out.println("任务名称:"+task.getName());
//            System.out.println("任务负责人:"+task.getAssignee());
//        }
        taskService.setVariable("80002","holiday",new Holiday().setNum(9));

        taskService.complete("80002");
    }
    /**
     * 使用压缩包进行流程部署
     */
    @Test
    public void testZipDeploy() throws FileNotFoundException {
        ProcessEngineConfiguration processEngineConfiguration=
                ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml",
                        "processEngineConfiguration");
        ProcessEngine processEngine=processEngineConfiguration.buildProcessEngine();
        //获得zip输入流
        InputStream inputStream=new FileInputStream("D:/IDEA_workspace/demo_springboot/workflow/src/main/resources/diagram/fileupload.zip");

        ZipInputStream zipInputStream=new ZipInputStream(inputStream);
        //通过RepositoryService进行部署
        Deployment deployment=processEngine
                .getRepositoryService()
                .createDeployment()
                .addZipInputStream(zipInputStream)
                .name("使用zip进行部署的测试")
                .deploy();
        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
    }

    //测试使用getResourceAsStream进行文件复制, 主要为确定它的读取路径的问题
    @Test
    public void testPath() throws IOException {
        InputStream in=this.getClass()
                .getClassLoader()
                .getResourceAsStream("diagram/fileupload.zip");
        OutputStream outputStream=new FileOutputStream("D:/fileupload.zip");
        byte[] b=new byte[1024];
        int i;
        if(null!=in){
            while((i=in.read(b))!=-1){
                outputStream.write(b,0,i);
            }
        }
    }
    //测试getResource的路径
    @Test
    public void testFilePath(){
        String path=this.getClass().getClassLoader().getResource("diagram/fileupload.bpmn").getFile().toString();
        System.out.println(path);
    }

}


















