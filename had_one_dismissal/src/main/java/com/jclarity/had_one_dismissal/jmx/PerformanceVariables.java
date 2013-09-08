package com.jclarity.had_one_dismissal.jmx;

import org.springframework.stereotype.Component;

import com.jclarity.crud_common.api.PerformanceVariablesMXBean;
import com.jclarity.crud_common.jmx.JMXComponent;

@Component
public class PerformanceVariables extends JMXComponent implements PerformanceVariablesMXBean {

    private int threadPoolSize;

    private int perturbingObjectSize;
    private int perturbingObjectQuantity;
    private int retainedObjectSize;
    private int retainedObjectQuantity;
    private int retainedObjectPoolSize;

    public PerformanceVariables() throws Exception {
        register(PerformanceVariablesMXBean.ADDRESS);
    }

    @Override
    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    @Override
    public void setThreadPoolSize(int size) {
        threadPoolSize = size;
    }

    @Override
    public int getPerturbingObjectSize() {
        return perturbingObjectSize;
    }

    @Override
    public void setPerturbingObjectSize(int sizeInB) {
        MemoryPerturbator.checkSize(sizeInB);
        perturbingObjectSize = sizeInB;
    }

    @Override
    public int getPerturbingObjectQuantity() {
        return perturbingObjectQuantity;
    }

    @Override
    public void setPerturbingObjectQuantity(int numberPerCall) {
        perturbingObjectQuantity = numberPerCall;
    }

    @Override
    public int getRetainedObjectSize() {
        return retainedObjectSize;
    }

    @Override
    public void setRetainedObjectSize(int sizeInB) {
        MemoryPerturbator.checkSize(sizeInB);
        retainedObjectSize = sizeInB;
    }

    @Override
    public int getRetainedObjectQuantity() {
        return retainedObjectQuantity;
    }

    @Override
    public void setRetainedObjectQuantity(int numberPerCall) {
        retainedObjectQuantity = numberPerCall;
    }

    @Override
    public int getRetainedObjectPoolSize() {
        return retainedObjectPoolSize;
    }

    @Override
    public void setRetainedObjectPoolSize(int sizeInB) {
        MemoryPerturbator.checkSize(sizeInB);
        retainedObjectPoolSize = sizeInB;
    }

}
