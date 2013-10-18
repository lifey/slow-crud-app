package com.jclarity.had_one_dismissal.exercises;



import static java.lang.Integer.parseInt;

import com.jclarity.had_one_dismissal.Exercise;
import com.jclarity.had_one_dismissal.api.Job;

/**
 * Performs two tasks, creating job/company listing then deleting it
 */
public class SearchDiskReadExercise extends Exercise {

    class SearchIndex extends Job {
        public SearchIndex(Exercise exercise) {
            super(exercise);
        }

        @Override
        protected void runJob() throws Exception {
            hadOneDismissal.searchIndex();
        }
    }

    public SearchDiskReadExercise(String threadCount) {
        super(parseInt(threadCount));
    }

    @Override
    public void runExercise() {
        for (int i = 0; i < poolSize; i++) {
            threadPool.execute(new SearchIndex(this));
        }
    }

}
