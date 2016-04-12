package com.h3c.incident.controller;

import com.h3c.common.Constants;
import com.h3c.common.hibernate.exception.SearchException;
import com.h3c.user.controller.BaseFormController;
import com.h3c.user.service.UserManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
        }
        return new ModelAndView("incident/incidentList", model.asMap());
    }
}
