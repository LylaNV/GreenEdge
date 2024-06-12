package com.github.lylanv.greenedge.inspections;

import com.github.lylanv.greenedge.inspections.GreenMeter;
import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

public class LogData {
    private String redAPICallName;
    private String className;
    private int lineNumber;
    private static Map<String, Integer> numberOfCalls = new HashedMap(); // A counter to track number of specific API calls

    public LogData(final String redAPICallName, final String className, final int lineNumber) {
        this.redAPICallName = redAPICallName;
        this.className = className;
        this.lineNumber = lineNumber;
    }

    public void setNumberOfCalls(String redAPICallName) {
        if (numberOfCalls.containsKey(redAPICallName)) {
            numberOfCalls.put(redAPICallName, numberOfCalls.get(redAPICallName) + 1);
        }else {
            numberOfCalls.put(redAPICallName, 1);
        }
    }

    public int getNumberOfCalls(String redAPICallName) {
        if (numberOfCalls.containsKey(redAPICallName)) {
            return numberOfCalls.get(redAPICallName);
        }else {
            return 0;
        }
    }

    public String getRedAPICallName() {
        return redAPICallName;
    }

    public void setRedAPICallName(final String redAPICallName) {
        this.redAPICallName = redAPICallName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(final String className){
        this.className = className;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(final int lineNumber) {
        this.lineNumber = lineNumber;
    }

}
