package com.h3c.activiti.service;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

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
public interface ProcessService {

    /**
     * 启动流程
     *
     * @param key       流程定义标识
     * @param variables 流程变量
     * @return 流程实例的id
     */
    String startProcess(String key, Map<String, Object> variables);

    /**
     * 查询所有的任务列表
     *
     * @return 任务列表
     */
    List<Task> listAllTasks();

    /**
     * 查询满足条件的的任务列表
     * @param variables
     * @return
     */
    List<Task> listTasks(Map<String, Object> variables);

    /**
     * 查询所有的任务列表
     *
     * @return 任务列表
     */
    List<ProcessDefinition> listAllProcDefs();

    /**
     * 部署流程
     *
     * @param filePath 流程文档路径
     * @return 流程定义id
     */
    String deployProcess(String filePath, String deployName);

    /**
     * 完成任务
     *
     * @param id 任务id
     * @return
     */
    String completeTask(String id);
}
