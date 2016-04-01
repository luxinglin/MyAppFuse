package com.h3c.activiti.service.impl;

import com.h3c.activiti.service.ProcessService;
import com.h3c.common.hibernate.exception.SearchException;
import com.h3c.common.util.ToyUtil;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: &lt;br&gt;
 *
 * @author Lu, Xing-Lin&lt;br&gt;
 * @version 1.0&lt;br&gt;
 * @taskId &lt;br&gt;
 * @createDate 2016/4/1 &lt;br&gt;
 */
@Service("processService")
public class ProcessServiceImpl implements ProcessService {
    private static final Log log = LogFactory.getLog(ProcessServiceImpl.class);
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Override
    public String startProcess(String key, Map<String, Object> variables) {
        String ret = null;
        if (ToyUtil.isNullOrEmpty(key))
            return ret;
        try {
            ProcessInstance instance = runtimeService.startProcessInstanceByKey(key, variables);
            ret = instance.getId();
            log.info("流程启动成功, 实例ID为: " + instance.getId());
        } catch (Exception ex) {
            log.info("流程启动失败\n" + ex.getMessage());
        }
        return ret;
    }

    @Override
    public List<Task> listAllTasks() {
        List<Task> taskList = null;
        try {
            taskList = taskService.createTaskQuery().list();
            log.info("任务查询成功，返回任务条数为: " + taskList.size());
        } catch (Exception ex) {

        }
        return taskList;
    }

    @Override
    public List<ProcessDefinition> listAllProcDefs() {
        List<ProcessDefinition> list = null;
        try {
            list = repositoryService.createProcessDefinitionQuery().list();
            log.info("流程定义查询成功，返回流程定义条数为: " + list.size());
        } catch (Exception ex) {

        }
        return list;
    }

    @Override
    public String deployProcess(String filePath, String deployName) {
        String ret = null;
        if (ToyUtil.isNullOrEmpty(filePath))
            return ret;
        try {
            Deployment deployment = repositoryService.createDeployment().name(deployName)
                    .addClasspathResource(filePath)
                    .deploy();
            ret = deployment.getName();
            log.info("流程部署成功，部署id为: " + deployment.getId());
        } catch (Exception ex) {

        }
        return ret;
    }

    @Override
    public String completeTask(String id) {
        String ret = "fail";
        if (ToyUtil.isNullOrEmpty(id))
            return ret;

        try {
            List<Task> tasks = taskService.createTaskQuery().taskId(id).list();
            if (tasks.size() > 0) {
                Task task = tasks.get(0);
                //task.getDescription();
                if (task.getName().equals("Handle vacation request")) {
                    Map<String, Object> taskVariables = new HashMap<String, Object>();
                    String passed = System.currentTimeMillis() % 2 == 0 ? "true" : "false";
                    taskVariables.put("vacationApproved", passed);
                    taskVariables.put("managerMotivation", "We have a tight deadline!");
                    taskService.complete(task.getId(), taskVariables);
                } else if ("Adjust vacation request".equals(task.getName())) {
                    Map<String, Object> taskVariables = new HashMap<String, Object>();
                    taskVariables.put("resendRequest", "true");
                    taskService.complete(task.getId(), taskVariables);
                } else {
                    taskService.complete(task.getId());
                }
                log.info("completeTask success with id: " + id);
            }
            ret = "success";
        } catch (SearchException se) {
        } catch (Exception ex) {
            log.error("completeTask failed:\n" + ex.getMessage());
        }
        return ret;
    }
}
