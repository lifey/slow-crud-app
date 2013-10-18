package com.jclarity.had_one_dismissal;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jclarity.crud_common.api.AuthServicePerformanceVariablesMXBean;
import com.jclarity.crud_common.api.PerformanceProblemsMXBean;
import com.jclarity.had_one_dismissal.jmx.AuthServiceJMXConnection;
import com.jclarity.had_one_dismissal.jmx.PerformanceProblemsJMXConnection;
import com.jclarity.had_one_dismissal.record.Recorder;
import com.jclarity.had_one_dismissal.record.TimingStatistics;

public abstract class Exercise {

    private static Logger LOGGER = LoggerFactory.getLogger(Exercise.class);

    protected final ScheduledThreadPoolExecutor threadPool;
    protected final int poolSize;
    protected final PerformanceProblemsMXBean performanceProblems;
    protected final AuthServicePerformanceVariablesMXBean authServicePerformanceVariables;

    private final PerformanceProblemsJMXConnection appJmxConnection;
    private final AuthServiceJMXConnection authJmxConnection;
    
    private Recorder recorder;

    protected volatile boolean isRunning = true;

    public static void runExercise(String clazzName, long timeLimitInMs, String[] arguments) {
        try {
            @SuppressWarnings("unchecked")
            Class<? extends Exercise> type = (Class<? extends Exercise>) Class.forName(clazzName);
            if (type != null) {
                Exercise exercise = newInstance(type, arguments);
                exercise.runExercise(timeLimitInMs);
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("failed to load exercise", e);
        }
    }

    private static Exercise newInstance(Class<? extends Exercise> type, String[] arguments)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException {

        if (arguments.length == 0)
            return type.newInstance();
        
        return (Exercise) type.getConstructors()[0]
                              .newInstance((Object[]) arguments);
    }

    public Exercise() {
        this(Runtime.getRuntime().availableProcessors());
    }

    public Exercise(int poolSize) {
        this.poolSize = poolSize;
        threadPool = new ScheduledThreadPoolExecutor(poolSize);

        appJmxConnection = new PerformanceProblemsJMXConnection();
        performanceProblems = appJmxConnection.getPerformanceProblemsMXBean();

        authJmxConnection = new AuthServiceJMXConnection();
        authServicePerformanceVariables = authJmxConnection.getAuthServicePerformanceVariables();
        recorder = new Recorder();
    }

    protected void stop() {
        LOGGER.info("Stopping @ " + System.currentTimeMillis());
        TimingStatistics timings = recorder.aggregate();
        LOGGER.info(timings.toString());
        try {
            reset();
            recorder = new Recorder();
            isRunning = false;
            try {
                threadPool.shutdown();
                threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                LOGGER.debug(e.getMessage(), e);
            }
        } finally {
            appJmxConnection.close();
            authJmxConnection.close();
        }
        LOGGER.info("STOPPED @ " + System.currentTimeMillis());
    }

    public void runExercise(long timeLimitInMs) {
        init();
        LOGGER.info("Starting @ " + System.currentTimeMillis());
        runExercise();
        long end = System.currentTimeMillis() + timeLimitInMs;
        while (isRunning && System.currentTimeMillis() < end) {
            try {
                Thread.sleep(end - System.currentTimeMillis());
            } catch (InterruptedException e) {
                // Deliberately ignore
            }
        }
        stop();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public abstract void runExercise();

    public void init() {
    }

    public void reset() {
    }

    public Recorder getRecorder() {
        return recorder;
    }

}
