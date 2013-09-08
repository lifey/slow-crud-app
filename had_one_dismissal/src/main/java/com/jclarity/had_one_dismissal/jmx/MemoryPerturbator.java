package com.jclarity.had_one_dismissal.jmx;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The memory perturbator allows the simulation of some common memory usage scenarios
 *
 * Real-life similar situations
 * - A normal application that creates many objects for internal purposes
 * - An application that put pressure on hardware by creating too many big objects
 * - A cache of small long-lived objects (like Hibernate)
 * - An application that caches in memory chunks of files
 *
 * Memory problems to simulate
 *
 * In order to simulate these problems, you can add the following allocation patterns to each Job Search.
 * - create many small objects that are not retained
 * - create 40 big objects of 1 MB that are not retained (40 MB object)
 * - some small objects that are retained
 * - a big object that is retained
 *
 * You retain the objects by storing them in an internal list with bounded size.
 */
@Component
public class MemoryPerturbator {

    private static final int ARRAY_HEADER_SIZE;
    private static final int INT_SIZE;

    static {
        String model = System.getProperty("sun.arch.data.model");
        boolean is32Bit = "32".equals(model);
        // Technically this ignores compressed oops
        ARRAY_HEADER_SIZE = is32Bit ? 12 : 20;
        INT_SIZE = 4;
    }

    public static void checkSize(int size) {
        if (size < ARRAY_HEADER_SIZE)
            throw new IllegalArgumentException(size + " must be at least " + ARRAY_HEADER_SIZE);
    }

    @Autowired private PerformanceVariables variables;

    private final List<int[]> objectPool;
    private int currentlyPooled;

    public MemoryPerturbator() {
        objectPool = new ArrayList<int[]>();
        currentlyPooled = 0;
    }

    public synchronized void perturbMemory() {
        allocateNonRetainedObjects();
        allocateRetainedObjects();
    }

    private void allocateNonRetainedObjects() {
        int perturbingObjectSize = variables.getPerturbingObjectSize();
        if (perturbingObjectSize == 0)
            return;

        allocateObjects(perturbingObjectSize, variables.getPerturbingObjectQuantity(), false);
    }

    protected void allocateObjects(int objectSize, int objectQuantity, boolean addToPool) {
        int length = calculateRequiredIntLength(objectSize);
        for (int i = 0; i < objectQuantity; i++) {
            int[] object = allocateArray(length);
            if (addToPool) {
                objectPool.add(object);
            }
        }

        if (addToPool) {
            int memoryAllocated = objectSize * objectQuantity;
            currentlyPooled += memoryAllocated;
        }
    }

    protected int[] allocateArray(int length) {
        return new int[length];
    }

    private int calculateRequiredIntLength(int objectSize) {
        int bodySize = objectSize - ARRAY_HEADER_SIZE;
        return bodySize / INT_SIZE;
    }

    private void allocateRetainedObjects() {
        int retainedObjectSize = variables.getRetainedObjectSize();
        if (retainedObjectSize == 0)
            return;

        if (currentlyPooled >= variables.getRetainedObjectPoolSize()) {
            return;
        }

        allocateObjects(retainedObjectSize, variables.getRetainedObjectQuantity(), true);
    }

}
