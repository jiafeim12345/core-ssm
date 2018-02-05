package me.cloudcat.develop.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

/**
 * 调度任务触发器监听器
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/5 11:20
 */
public class TaskTriggerListener implements TriggerListener{

    @Override
    public String getName() {
        return "TaskTriggerListener";
    }

    /**
     * 触发定时任务后第一个执行的方法
     *
     * @param trigger
     * @param context
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {

    }

    /**
     * 判断定时任务是否可以执行 没有TaskLock注解的任务直接执行，
     * 有TaskLock的任务在执行前先去redis获取锁
     *
     * @param trigger
     * @param context
     * @return
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    /**
     * 定时任务错过执行时间后执行的方法
     *
     * @param trigger
     */
    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    /**
     * 定时任务成功执行后执行的方法
     *
     * @param trigger
     * @param context
     * @param triggerInstructionCode
     */
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {

    }
}
