package com.jclarity.crud_common.api;

public interface PerformanceProblemsMXBean {

    public static final String ADDRESS = "com.jclarity.had_one_dismissal:type=PerformanceProblems";

    public boolean isDeadlockEnabled();

    public void setDeadlockEnabled(boolean enabled);

    public boolean isSavingDBQueries();

    public void setSavingDBQueries(boolean batching);

    public Database getDatabaseType();

    public void setDatabaseType(Database database);

    public String getRootLoggingLevel();

    public void setRootLoggingLevel(String level);

    public boolean isSavingLoadedData();

    public void setSavingLoadedData(boolean savingLoadedData);

}
