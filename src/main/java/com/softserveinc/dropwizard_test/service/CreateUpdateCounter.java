package com.softserveinc.dropwizard_test.service;

public class CreateUpdateCounter {

    private int totalUpdatesCount, totalCreatesCount, currentUpdatesCount, currentCreatesCount;

    public void increaseUpdatesCount() {
        currentUpdatesCount++;
        totalUpdatesCount++;
    }

    public void increaseCreatesCount() {
        currentCreatesCount++;
        totalCreatesCount++;
    }

    public int getTotalUpdatesCount() {
        return totalUpdatesCount;
    }

    public int getTotalCreatesCount() {
        return totalCreatesCount;
    }

    public int getCurrentUpdatesCount() {
        if (currentUpdatesCount > 0) {
            int retVal = currentUpdatesCount;
            currentUpdatesCount = 0;
            return retVal;
        } else {
            return currentUpdatesCount;
        }

    }

    public int getCurrentCreatesCount() {
        if (currentCreatesCount > 0) {
            int retVal = currentCreatesCount;
            currentCreatesCount = 0;
            return retVal;
        } else {
            return currentCreatesCount;
        }
    }
}
