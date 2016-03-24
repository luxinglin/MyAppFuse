package org.activiti.test;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by l61989 on 2016/3/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-resources.xml")
public class FixSystemTest {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Test
    @Deployment
    public void failureProcessTest() {
        //启动故障修复流程
        runtimeService.startProcessInstanceByKey("fixSystemFailure");

        //主流程启动后包括2个流程实例
        assertEquals(1, runtimeService.createProcessInstanceQuery().count());

        //engineering
        //management
        final String lev1Engineering = "engineering";
        final String lev2Engineering = "management";

        //一线工程师查看、处理任务
        List<Task> taskList = taskService.createTaskQuery().taskCandidateGroup(lev1Engineering).list();
        //并行任务单
        assertEquals(2, taskService.createTaskQuery().count());

        int seconds = 31;
        for (Task task : taskList) {
            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                taskService.complete(task.getId());
            } catch (Exception ex) {
                System.out.println("流程超时，该任务[" + task.getName() + "]已关闭");
            }
        }
        //子流程处理完毕，只剩一个流程实例
        assertEquals(1, runtimeService.createProcessInstanceQuery().count());

        //一个报告任务单
        assertEquals(1, taskService.createTaskQuery().count());

        //提交报告
        taskList = taskService.createTaskQuery().taskCandidateGroup(lev1Engineering).list();
        for (Task task : taskList) {
            taskService.complete(task.getId());
        }

        //超时升级
        taskList = taskService.createTaskQuery().taskCandidateGroup(lev2Engineering).list();
        for (Task task : taskList) {
            taskService.complete(task.getId());
        }

        //流程正常结束
        assertEquals(0, runtimeService.createProcessInstanceQuery().count());
    }
}
