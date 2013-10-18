package com.jclarity.had_one_dismissal.record;

import java.util.List;

import com.google.common.collect.Lists;
import com.gs.collections.api.list.primitive.LongList;
import com.gs.collections.api.list.primitive.MutableLongList;
import com.gs.collections.impl.list.mutable.primitive.LongArrayList;

public class Recorder {

    private final List<Stopwatch> watches;

    public Recorder() {
        watches = Lists.newArrayList();
    }

    public synchronized Stopwatch newWatch() {
        Stopwatch watch = new Stopwatch();
        watches.add(watch);
        return watch;
    }

    public TimingStatistics aggregate() {
        MutableLongList allTimings = new LongArrayList();
        for (Stopwatch watch : watches) {
            LongList timings = watch.getTimings();
            allTimings.addAll(timings);
        }
        return calculateStats(allTimings);
    }

    private TimingStatistics calculateStats(MutableLongList timings) {
        timings.sortThis();
        
        return new TimingStatistics(timings.average(),
                                    percentile(timings, 0.5),
                                    percentile(timings, 0.9),
                                    percentile(timings, 0.99),
                                    timings.getLast());
    }

    private long percentile(LongList sortedList, double percentile) {
        double index = percentile * (sortedList.size() - 1);
        double round = Math.round(index);
        if (round == index) {
            return sortedList.get((int) index);
        }

        double floor = Math.floor(index);
        double lower = sortedList.get((int) floor);
        double upper = sortedList.get((int) floor + 1);
        return (long) Math.round((lower + upper) / 2);
    }

}
