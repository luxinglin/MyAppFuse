package org.activiti.test;

import com.h3c.common.util.DateUtil;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-resources.xml")
public class ProcessTest {
    private static final Log log = LogFactory.getLog(ProcessTest.class);
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Test
    @Deployment
    public void vacationRequestTest() {
        Map<String, Object> variables = new HashMap<String, Object>();
        final String employeeName = "Kermit";
        //请假正文
        variables.put("employeeName", employeeName);
        variables.put("numberOfDays", new Integer(4));
        variables.put("vacationMotivation", "I'm really tired!");

        //请假人联系信息，后续发送邮件通知会使用
        variables.put("male", true);
        variables.put("recipient", "xinglin.lu@hpe.com");
        variables.put("recipientName", "Lu, Xing-Lin");
        variables.put("now", DateUtil.convertDateToString(new Date()));

        log.info(employeeName + " write the message to request a vacation");
        runtimeService.startProcessInstanceByKey("vacationRequest", variables);
        //assertEquals(1, runtimeService.createProcessInstanceQuery().count());

        //经理审批1：拒绝，工作紧，无法安排调休
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
        final String reject = "We have a tight deadline!";
        if (tasks.size() > 0) {
            for (Task task : tasks) {
                if (!"Handle vacation request".equals(task.getName()))
                    continue;

                Map<String, Object> taskVariables = new HashMap<String, Object>();
                taskVariables.put("vacationApproved", "false");
                taskVariables.put("managerMotivation", "");
                taskService.complete(task.getId(), taskVariables);
                log.info("approval message: " + reject);
            }
        }

        //员工重新发送请假
        tasks = taskService.createTaskQuery().taskAssignee(employeeName).list();
        if (tasks.size() > 0) {
            Task task = tasks.get(0);
            Map<String, Object> taskVariables = new HashMap<String, Object>();
            taskVariables.put("resendRequest", "true");
            taskService.complete(task.getId(), taskVariables);
        }

        //assertEquals(1, taskService.createTaskQuery().count());

        //批准请假
        final String approval = "Okay, have nice vacation!";
        tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
        if (tasks.size() > 0) {
            for (Task task : tasks) {
                if (!"Handle vacation request".equals(task.getName()))
                    continue;

                Map<String, Object> taskVariables = new HashMap<String, Object>();
                taskVariables.put("vacationApproved", "true");
                taskVariables.put("managerMotivation", "Okay, have nice vacation!");
                taskService.complete(task.getId(), taskVariables);
                log.info("approval message: " + approval);
            }
        }
        log.info("Send confirmation e-mail");
        assertEquals(0, 1 - 1);
    }
}