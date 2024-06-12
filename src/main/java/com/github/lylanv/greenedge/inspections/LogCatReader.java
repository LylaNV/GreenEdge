package com.github.lylanv.greenedge.inspections;

import com.android.tools.idea.adb.AdbShellCommandsUtil;
import org.jetbrains.kotlin.serialization.js.ast.JsAstProtoBuf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.HashMap;
import org.jfree.data.category.DefaultCategoryDataset;

//public class LogCatReader implements Runnable {
public class LogCatReader implements Runnable {
    private LogcatAnalyzerToolWindow toolWindow;
    private final String TAG;
    private volatile boolean running = true; // Volatile to ensure visibility across threads
    Map<String, Integer> redAPIsCount = new HashMap<>(); // Holds the name of APIs and their counts
    private DefaultCategoryDataset dataset;

    public LogCatReader(LogcatAnalyzerToolWindow toolWindow, String TAG) {
        this.toolWindow = toolWindow;
        this.TAG = TAG;
    }

    @Override
    public void run() {
        try {
            Process logcatProcess = Runtime.getRuntime().exec("adb logcat");
            BufferedReader logcatReader = new BufferedReader(new InputStreamReader(logcatProcess.getInputStream()));
            String line;
            while ((line = logcatReader.readLine()) != null && running) {
                if (line.contains(TAG)) {
                    //logcatOutput.append(line).append("\n");
                    toolWindow.appendLog(line);

                    String extractedAPICallName = getAPICallName(line);
                    redAPIsCount.put(extractedAPICallName, redAPIsCount.getOrDefault(extractedAPICallName, 0) + 1);
                    updateGraph(redAPIsCount);
                }
            }
            logcatReader.close();
            logcatProcess.destroy();
            stop();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Gets API/method call name from the logged line
    private String getAPICallName(String line) {
        // finds the index of the open parentheses signe ("(") in the logged line
        int index = line.indexOf('(');
        if (index < 0) {
            return null;
        }

        // finds the index of the first comma after open parentheses signe ("(") in the logged line
        int firstComma = line.indexOf(',', index + 1);
        if (firstComma < 0) {
            return null;
        }

        // returns the first argument in the parentheses
        return line.substring(index + 1, firstComma).trim();

    }

    public void stop() throws IOException {
        //running = false;
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("adb logcat -c");
        dataset.clear();
        redAPIsCount.clear();
        toolWindow.deleteLogcatAnalyzerToolWindow();
    }

    // Updates the bar graph
    private void updateGraph(Map<String, Integer> logLevelCount) {
        dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Integer> entry : logLevelCount.entrySet()) {
            dataset.addValue(entry.getValue(), entry.getKey(), "");
        }

        toolWindow.updateGraph(dataset);
    }

}
