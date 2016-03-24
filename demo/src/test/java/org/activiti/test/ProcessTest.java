package org.activiti.test;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-resources.xml")
public class ProcessTest {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    @Rule
    public ActivitiRule activitiSpringRule;

    @Test
    @Deployment
    public void vacationRequestTest() {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employeeName", "Kermit");
        variables.put("numberOfDays", new Integer(4));
        variables.put("vacationMotivation", "I'm really tired!");

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("vacationRequest", variables);

        assertEquals(1, runtimeService.createProcessInstanceQuery().count());
    }

    @Test
    public void countProcessInstanceTest() {
        assertEquals(0, runtimeService.createProcessInstanceQuery().count());
    }

    /**
     * 测试任务查询功能
     */
    @Test
    public void queryTaskTest() {
        List<Task> tasks = taskService.createTaskQuery().list();
        for (Task task : tasks) {
            System.out.println("Process InstanceId available: " + task.getProcessInstanceId());
            System.out.println("Task available: " + task.getName());
        }
        assertEquals(0, tasks.size());
    }

    /**
     * 测试完成任务
     */
    @Test
    public void completeTaskTest() {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser("Kermit").list();

        if (tasks.size() > 0) {
            Task task = tasks.get(0);

            Map<String, Object> taskVariables = new HashMap<String, Object>();
            taskVariables.put("vacationApproved", "true");
            taskVariables.put("managerMotivation", "We have a tight deadline!");
            taskService.complete(task.getId(), taskVariables);
        }
    }

    /**
     * 测试流程定义删除功能
     */
    @Test
    public void deleteProcessDefineTest() {
        List<ProcessDefinition> defines = repositoryService.createProcessDefinitionQuery().list();
        for (ProcessDefinition define : defines) {
            //repositoryService.deleteDeployment(define.getDeploymentId());
        }
        assertEquals(5, repositoryService.createProcessDefinitionQuery().count());
    }

    /**
     * 测试流程实例删除功能
     */
    @Test
    public void deleteProcessInstanceTest() {
        List<ProcessInstance> insts = runtimeService.createProcessInstanceQuery().list();
        for (ProcessInstance inst : insts) {
            //runtimeService.deleteProcessInstance(inst.getId(), null);
        }
        assertEquals(0, runtimeService.createProcessInstanceQuery().count());
    }
}