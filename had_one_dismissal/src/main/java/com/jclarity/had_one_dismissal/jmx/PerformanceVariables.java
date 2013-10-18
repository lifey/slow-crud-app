package com.jclarity.had_one_dismissal.jmx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jclarity.crud_common.api.PerformanceVariablesMXBean;
import com.jclarity.crud_common.jmx.JMXComponent;

@Component
public class PerformanceVariables extends JMXComponent implements PerformanceVariablesMXBean {
    
    private final JettyConfigurationEditor configurationEditor;

    private int minThreadPoolSize;
    private int maxThreadPoolSize;

    private int perturbingObjectSize;
    private int perturbingObjectQuantity;
    private int retainedObjectSize;
    private int retainedObjectQuantity;
    private int retainedObjectPoolSize;

    @Autowired
    public PerformanceVariables(JettyConfigurationEditor configurationEditor) throws Exception {
        this.configurationEditor = configurationEditor;
        
        minThreadPoolSize = configurationEditor.readMinThreadPoolSize();
        maxThreadPoolSize = configurationEditor.readMaxThreadPoolSize();
        
        perturbingObjectSize = 0;
        perturbingObjectQuantity = 0;
        retainedObjectSize = 0;
        retainedObjectQuantity = 0;
        retainedObjectPoolSize = 0;
        
        register(PerformanceVariablesMXBean.ADDRESS);
    }
    
    @Override
    public int getMinThreadPoolSize() {
        return minThreadPoolSize;
    }

    @Override
    public void setMinThreadPoolSize(int size) {
        this.minThreadPoolSize = size;
        configurationEditor.writeMinThreadPoolSize(size);
    }

    @Override
    public int getMaxThreadPoolSize() {
        return maxThreadPoolSize;
    }

    @Override
    public void setMaxThreadPoolSize(int size) {
        maxThreadPoolSize = size;
        configurationEditor.writeMaxThreadPoolSize(size);
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
