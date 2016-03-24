package com.h3c.restful.controller;

import com.h3c.user.model.User;
import com.h3c.user.service.UserManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class SpringRestController {
    private UserManager userManager = null;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    protected User loadUser(@PathVariable String id) {
        if (StringUtils.isNotBlank(id)) {
            return getUserManager().getUser(id);
        }
        return new User();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    protected List<User> listUser() {
        return getUserManager().getAll();
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public UserManager getUserManager() {
        return this.userManager;
    }
}

