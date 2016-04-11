package com.h3c.incident.controller;

import com.h3c.activiti.service.ProcessService;
import com.h3c.common.Constants;
import com.h3c.common.hibernate.exception.SearchException;
import com.h3c.common.util.DateUtil;
import com.h3c.common.util.ToyUtil;
import com.h3c.user.controller.BaseFormController;
import com.h3c.user.service.UserManager;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
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
 * @createDate 2016/3/31 &lt;br&gt;
 */
@Controller
@RequestMapping("/incident")
public class IncidentController extends BaseFormController {
    private static final Log log = LogFactory.getLog(IncidentController.class);

    @Autowired
    private UserManager userManager = null;
    @Autowired
    private ProcessService processService;

    public IncidentController() {
        setSuccessView("redirect:/incident/listTask");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/listIncident")
    public ModelAndView listIncident() throws Exception {
        log.info("try to load all incidents");
        Model model = new ExtendedModelMap();
        try {
            model.addAttribute(Constants.USER_LIST, userManager.search(null));
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(userManager.getUsers());
        }
        return new ModelAndView("incident/incidentList", model.asMap());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/listTask")
    public ModelAndView listTask() {
        Model model = new ExtendedModelMap();
        try {
            List<Task> taskList = this.processService.listAllTasks();
            model.addAttribute("incidentList", taskList);
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
        }
        return new ModelAndView("incident/taskList", model.asMap());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/listProcDef")
    public ModelAndView listProcDef() throws Exception {
        Model model = new ExtendedModelMap();
        try {
            List<ProcessDefinition> list = this.processService.listAllProcDefs();
            model.addAttribute("procList", list);
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(userManager.getUsers());
        }
        return new ModelAndView("incident/procDefList", model.asMap());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/completeTask")
    public String completeTask(@RequestParam(required = false) String id) {
        String ret = this.processService.completeTask(id);
        log.info("completeTask: " + ret);
        return getSuccessView();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/startVacationRequest")
    public String startVacationRequest() {
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

        String instId = processService.startProcess("vacationRequest", variables);
        log.info(employeeName + " startVacationRequest successfully with id: " + instId);

        return this.getSuccessView();
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
        return "incident/procDefList";
    }
}
