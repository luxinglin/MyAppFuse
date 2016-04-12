package com.h3c.activiti.controller;

import com.h3c.activiti.service.ProcessService;
import com.h3c.common.hibernate.exception.SearchException;
import com.h3c.user.controller.BaseFormController;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/task")
public class TaskController extends BaseFormController {
    private static final Log log = LogFactory.getLog(TaskController.class);
    @Autowired
    private ProcessService processService;

    public TaskController() {
        setSuccessView("redirect:/task/todoTask");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/todoTask")
    public ModelAndView todoTask(HttpServletRequest request) {
        Model model = new ExtendedModelMap();
        try {
            Map<String, Object> variables = null;
            String fullName = this.getLoginUserName(request);
            if (fullName != null) {
                variables = new HashMap<>();
                variables.put("assignee", fullName.trim());
            }
            List<Task> taskList = this.processService.listTasks(variables);
            model.addAttribute("incidentList", taskList);
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
        }
        return new ModelAndView("task/todoList", model.asMap());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/nonfinishTask")
    public ModelAndView nonfinishTask() {
        Model model = new ExtendedModelMap();
        try {
            List<Task> taskList = this.processService.listAllTasks();
            model.addAttribute("incidentList", taskList);
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
        }
        return new ModelAndView("task/nonfinishList", model.asMap());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/completeTask")
    @ResponseBody
    public String completeTask(@RequestParam(required = false) String id) {
        String ret = this.processService.completeTask(id);
        log.info("completeTask: " + ret);
        return ret;
    }
}
