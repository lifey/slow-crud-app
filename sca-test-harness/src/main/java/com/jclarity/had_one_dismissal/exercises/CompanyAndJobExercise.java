package com.jclarity.had_one_dismissal.exercises;



import com.jclarity.had_one_dismissal.Exercise;
import com.jclarity.had_one_dismissal.api.CreateCompanyAndJob;
import com.jclarity.had_one_dismissal.api.DeleteCompanyAndJob;
import com.jclarity.had_one_dismissal.api.HadOneDismissal;

/**
 * Performs two tasks, creating job/company listing then deleting it
 *
 */
public abstract class CompanyAndJobExercise extends Exercise {

    protected final HadOneDismissal hadOneDismissalApi = new HadOneDismissal();

    private final int enqueueJobsCount;
    private final int deleteJobsCount;

    public CompanyAndJobExercise(int enqueueJobsCount, int deleteJobsCount) {
        super(enqueueJobsCount + deleteJobsCount);
        this.enqueueJobsCount = enqueueJobsCount;
        this.deleteJobsCount = deleteJobsCount;
    }

    @Override
    public void runExercise() {
        for (int i = 0; i < enqueueJobsCount; i++) {
            threadPool.execute(new CreateCompanyAndJob(this));
        }

        for (int i = 0; i < deleteJobsCount; i++) {
            threadPool.execute(new DeleteCompanyAndJob(this));
        }
    }

}
