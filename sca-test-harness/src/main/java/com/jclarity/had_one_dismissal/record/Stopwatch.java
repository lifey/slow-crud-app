package com.jclarity.had_one_dismissal.record;

import com.gs.collections.api.list.primitive.LongList;
import com.gs.collections.api.list.primitive.MutableLongList;
import com.gs.collections.impl.list.mutable.primitive.LongArrayList;

public class Stopwatch {
    
    private final MutableLongList timings;
    
    private long startTime;

    public Stopwatch() {
        timings = new LongArrayList();
    }
    
    public void start() {
        startTime = System.nanoTime();
    }
    
    public void stop() {
        long time = System.nanoTime() - startTime;
        timings.add(time);
    }

    LongList getTimings() {
        return timings;
    }

}
