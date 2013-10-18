package com.jclarity.crud_common.api;

public interface PerformanceVariablesMXBean {

    public static final String ADDRESS = "com.jclarity.had_one_dismissal:type=PerformanceVariables";

    public int getMaxThreadPoolSize();

    public void setMaxThreadPoolSize(int size);

    public int getPerturbingObjectSize();

    public void setPerturbingObjectSize(int sizeInKb);

    public int getPerturbingObjectQuantity();

    public void setPerturbingObjectQuantity(int numberPerCall);

    public int getRetainedObjectSize();

    public void setRetainedObjectSize(int sizeInKb);

    public int getRetainedObjectQuantity();

    public void setRetainedObjectQuantity(int numberPerCall);

    public int getRetainedObjectPoolSize();

    public void setRetainedObjectPoolSize(int sizeInKb);

    public void setMinThreadPoolSize(int minThreadPoolSize);

    public int getMinThreadPoolSize();

}
