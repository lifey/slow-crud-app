package com.jclarity.had_one_dismissal.exercises;

public class CpuLoadExercise extends PopulateExercise {

    public CpuLoadExercise(String threadCount) {
        super(threadCount);
    }

    @Override
    public void init() {
        performanceProblems.setSavingDBQueries(false);
    }

    @Override
    public void reset() {
        performanceProblems.setSavingDBQueries(true);
    }

}
