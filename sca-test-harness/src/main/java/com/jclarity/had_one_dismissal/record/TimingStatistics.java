package com.jclarity.had_one_dismissal.record;

public class TimingStatistics {

    private static final long NS_IN_MS = 1000;
    
    private final double mean;
    private final long fiftiethPercentile;
    private final long ninetiethPercentile;
    private final long ninetyninthPercentile;
    private final long worst;

    public TimingStatistics(double mean, long fiftiethPercentile,
            long ninetiethPercentile, long ninetyninthPercentile, long worst) {
        this.mean = mean / NS_IN_MS;
        this.fiftiethPercentile = fiftiethPercentile / NS_IN_MS;
        this.ninetiethPercentile = ninetiethPercentile / NS_IN_MS;
        this.ninetyninthPercentile = ninetyninthPercentile / NS_IN_MS;
        this.worst = worst / NS_IN_MS;
    }

    public double getMean() {
        return mean;
    }

    public long getFiftiethPercentile() {
        return fiftiethPercentile;
    }

    public long getNinetiethPercentile() {
        return ninetiethPercentile;
    }

    public long getNinetyNinthPercentile() {
        return ninetyninthPercentile;
    }

    public long getWorst() {
        return worst;
    }

    @Override
    public String toString() {
        return "mean=" + mean + "\n50%=" + fiftiethPercentile + "\n90%="
                + ninetiethPercentile + "\n99%=" + ninetyninthPercentile
                + "\nworst=" + worst;
    }

}
