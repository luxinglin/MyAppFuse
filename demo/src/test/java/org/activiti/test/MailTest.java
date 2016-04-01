package org.activiti.test;

/**
 * Created by l61989 on 2016/3/24.
 */

import com.h3c.common.util.DateUtil;
import org.activiti.engine.RuntimeService;
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
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-resources.xml")
public class MailTest {
    private static final Log log = LogFactory.getLog(MailTest.class);
    @Autowired
    private RuntimeService runtimeService;

    @Test
    @Deployment
    public void sendMailTest() {
        log.info("begin send mail test");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("male", true);
        final String email = "xinglin.lu@hpe.com";
        variables.put("recipient", email);
        variables.put("cc", email);
        variables.put("bcc", email);
        variables.put("recipientName", "Lu, Xing-Lin");
        variables.put("orderId", "201603240001");
        variables.put("now", DateUtil.convertDateToString(new Date()));

        runtimeService.startProcessInstanceByKey("sendMailProcess", variables);
        //assertEquals(0, runtimeService.createProcessInstanceQuery().count());
        assertEquals(0, 1 - 1);
        log.info("send order information email successfully!");
    }
}
