package com.jclarity.had_one_dismissal.api;

import com.jclarity.had_one_dismissal.Exercise;
import com.jclarity.had_one_dismissal.record.Stopwatch;

public abstract class Job implements Runnable {

    protected HadOneDismissal hadOneDismissal = new HadOneDismissal();

    protected final Exercise exercise;

    public Job(Exercise exercise) {
        this.exercise = exercise;
    }

    @Override
    public void run() {
    	Stopwatch stopwatch = exercise.getRecorder().newWatch();
        while(exercise.isRunning()) {
        	stopwatch.start();
            try {
                runJob();
            } catch (Exception e) {
                // Deliberately ignored
            } finally {
            	stopwatch.stop();
            }
        }
    }

    protected abstract void runJob() throws Exception;

}
