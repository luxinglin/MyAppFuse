package com.h3c.activiti.controller;

import com.h3c.activiti.service.ProcessService;
import com.h3c.common.hibernate.exception.SearchException;
import com.h3c.common.util.DateUtil;
import com.h3c.common.util.ToyUtil;
import com.h3c.user.controller.BaseFormController;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: &lt;br&gt;
 *
 * @author Lu, Xing-Lin&lt;br&gt;
 * @version 1.0&lt;br&gt;
 * @taskId &lt;br&gt;
 * @createDate 2016/4/12 &lt;br&gt;
 */
@Controller
@RequestMapping("/process")
public class ProcessController extends BaseFormController {
    private static final Log log = LogFactory.getLog(ProcessController.class);
    @Autowired
    private ProcessService processService;

    public ProcessController() {
        setSuccessView("redirect:/process/listProcDef");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/listProcDef")
    public ModelAndView listProcDef() throws Exception {
        Model model = new ExtendedModelMap();
        try {
            List<ProcessDefinition> list = this.processService.listAllProcDefs();
            model.addAttribute("procList", list);
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
        }
        return new ModelAndView("incident/procDefList", model.asMap());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/toStartProcess")
    public ModelAndView toStartProcess() throws Exception {
        return new ModelAndView("incident/incidentRegist", null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/startVacationRequest")
    public String startVacationRequest() {
        Map<String, Object> variables = new HashMap<String, Object>();
        final String employeeName = "Matt Raible";
        //请假正文
        variables.put("employeeName", employeeName);
        variables.put("numberOfDays", new Integer(3));
        variables.put("vacationMotivation", "I'm really tired!");
        //请假人联系信息，后续发送邮件通知会使用
        variables.put("male", true);
        variables.put("recipient", "xinglin.lu@hpe.com");
        variables.put("recipientName", "Lu, Xing-Lin");
        variables.put("now", DateUtil.convertDateToString(new Date()));

        String instId = processService.startProcess("vacationRequest", variables);
        log.info(employeeName + " startVacationRequest successfully with id: " + instId);

        return "redirect:toStartProcess";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deployProcess")
    public String deployProcess(@RequestParam(required = false) String type) {
        if (ToyUtil.stringNotEmpty(type)) {
            Map<String, String> map = new HashMap<>();
            map.put("vacationRequest", "/bpmn/ProcessTest.vacationRequestTest.bpmn20.xml");
            map.put("fixSystemFailure", "/bpmn/FixSystemTest.failureProcessTest.bpmn20.xml");
            map.put("sendMailProcess", "/bpmn/MailTest.sendMailTest.bpmn20.xml");

            if (ToyUtil.stringNotEmpty(map.get(type))) {
                this.processService.deployProcess(map.get(type),
                        "deployProcess:" + type);
            }
        }
        return "redirect:listProcDef";
    }
}
