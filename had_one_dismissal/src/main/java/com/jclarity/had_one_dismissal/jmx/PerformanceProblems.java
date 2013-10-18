package com.jclarity.had_one_dismissal.jmx;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import com.jclarity.crud_common.api.Database;
import com.jclarity.crud_common.api.PerformanceProblemsMXBean;
import com.jclarity.crud_common.jmx.JMXComponent;

@Component
public class PerformanceProblems extends JMXComponent implements PerformanceProblemsMXBean {

    @Autowired private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    private boolean deadlockEnabled;
    private boolean savingDBQueries;
    private boolean savingLoadedData;
    private Database database;

    public PerformanceProblems() throws Exception {
        register(ADDRESS);
        setSavingLoadedData(true);
        deadlockEnabled = true;
        savingDBQueries = true;
        savingLoadedData = true;
        database = Database.IN_MEMORY;
    }

    @Override
    public boolean isDeadlockEnabled() {
        return deadlockEnabled;
    }

    @Override
    public void setDeadlockEnabled(boolean enabled) {
        deadlockEnabled = enabled;
    }

    @Override
    public boolean isSavingDBQueries() {
        return savingDBQueries;
    }

    @Override
    public void setSavingDBQueries(boolean saving) {
        savingDBQueries = saving;
    }

    @Override
    public Database getDatabaseType() {
        return database;
    }

    @Override
    public void setDatabaseType(Database database) {
        this.database = database;
        entityManagerFactory.afterPropertiesSet();
    }

    @Override
    public String getRootLoggingLevel() {
        return LogManager.getRootLogger().getLevel().toString();
    }

    @Override
    public void setRootLoggingLevel(String levelName) {
        Level level = Level.toLevel(levelName);
        LogManager.getRootLogger().setLevel(level);
    }

    @Override
    public boolean isSavingLoadedData() {
        return savingLoadedData;
    }

    @Override
    public void setSavingLoadedData(boolean savingLoadedData) {
        this.savingLoadedData = savingLoadedData;
    }

}
