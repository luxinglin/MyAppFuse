package com.h3c.activiti.event;

/**
 * Created by l61989 on 2016/3/24.
 * An event mechanism has been introduced in Activiti 5.15
 */
public class CustomActivitiEventListener {
    /*extends ActivitiEventListener  {
    @Override
    public void onEvent(ActivitiEvent event) {
        switch (event.getType()) {

            case JOB_EXECUTION_SUCCESS:
                System.out.println("A job well done!");
                break;

            case JOB_EXECUTION_FAILURE:
                System.out.println("A job has failed...");
                break;

            default:
                System.out.println("Event received: " + event.getType());
        }
    }

    @Override
    public boolean isFailOnException() {
        // The logic in the onEvent method of this listener is not critical, exceptions
        // can be ignored if logging fails...
        return false;
    }*/

}
