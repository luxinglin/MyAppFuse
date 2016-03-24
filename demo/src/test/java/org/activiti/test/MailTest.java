package org.activiti.test;

/**
 * Created by l61989 on 2016/3/24.
 */

import com.h3c.common.util.DateUtil;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-resources.xml")
public class MailTest {
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
    public void sendMailTest() {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("male", true);
        variables.put("recipient", "xinglin.lu@hpe.com");
        variables.put("cc", "651150151@qq.com");
        variables.put("bcc", "mail2lxl@qq.com");
        variables.put("recipientName", "Lu, Xing-Lin");
        variables.put("orderId", "201603240001");
        variables.put("now", DateUtil.convertDateToString(new Date()));

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("sendMailProcess", variables);
    }
}
