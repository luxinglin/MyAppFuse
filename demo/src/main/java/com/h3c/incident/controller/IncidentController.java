package com.h3c.incident.controller;

import com.h3c.common.Constants;
import com.h3c.common.hibernate.exception.SearchException;
import com.h3c.user.controller.BaseFormController;
import com.h3c.user.service.UserManager;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
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

import java.util.List;

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
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    public IncidentController() {
        setSuccessView("redirect:/incident/listTask");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/listIncident")
    public ModelAndView list() throws Exception {
        log.info("try to load all incidents");
        Model model = new ExtendedModelMap();
        try {
            model.addAttribute(Constants.USER_LIST, userManager.search(null));
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(userManager.getUsers());
        }
        startProcess();
        return new ModelAndView("incident/incidentList", model.asMap());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/listTask")
    public ModelAndView listTasks(@RequestParam(required = false)
                                  String lev1Engineering) {
        Model model = new ExtendedModelMap();
        try {
            log.error("listTasks with group: " + lev1Engineering);
            List<Task> taskList = taskService.createTaskQuery().list();
            model.addAttribute("incidentList", taskList);
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
        }

        return new ModelAndView("incident/taskList", model.asMap());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/completeTask")
    public String completeTask(@RequestParam(required = false) String id) {
        if (id == null || id.trim().isEmpty())
            return getSuccessView();
        try {
            taskService.complete(id);
            log.info("completeTask success with id: " + id);
        } catch (SearchException se) {
        } catch (Exception ex) {
            log.error("completeTask failed:\n" + ex.getMessage());
        }
        return getSuccessView();
    }

    private void startProcess() {
        //启动故障修复流程
        runtimeService.startProcessInstanceByKey("fixSystemFailure");
    }

}
