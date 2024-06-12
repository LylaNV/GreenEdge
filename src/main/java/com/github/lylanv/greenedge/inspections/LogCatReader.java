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
//    private LogcatAnalyzerToolWindow toolWindow;
//    private final String TAG;
//    private volatile boolean running = true; // Volatile to ensure visibility across threads
//
//
//    public LogCatReader(LogcatAnalyzerToolWindow toolWindow, String TAG) {
//        this.TAG = TAG;
//        this.toolWindow = toolWindow;
//    }
//
//    @Override
//    public void run() {
//        try {
//            //Process logcatProcess = Runtime.getRuntime().exec("logcat -v time");
//            Process logcatProcess = Runtime.getRuntime().exec("adb logcat");
//            BufferedReader logcatReader = new BufferedReader(new InputStreamReader(logcatProcess.getInputStream()));
//            String line;
//            while ((line = logcatReader.readLine()) != null && running) {
//                if (line.contains(TAG)) {
//                    //logcatOutput.append(line).append("\n");
//                    toolWindow.appendLog(line);
//                }
//            }
//            logcatReader.close();
//            logcatProcess.destroy();
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void stop(){
//        running = false;
//        //toolWindow.deleteContent();
//    }

//    public String readLogCat() throws IOException {
//        StringBuilder logcatOutput = new StringBuilder();
//        //Process logcatProcess = Runtime.getRuntime().exec("logcat -v time");
//        Process logcatProcess = Runtime.getRuntime().exec("adb logcat");
//        BufferedReader logcatReader = new BufferedReader(new InputStreamReader(logcatProcess.getInputStream()));
//        String line;
//        while ((line = logcatReader.readLine()) != null && running) {
//            if (line.contains(TAG)) {
//                logcatOutput.append(line).append("\n");
//            }
//        }
//        logcatReader.close();
//        logcatProcess.destroy();
//        return logcatOutput.toString();
//    }

    private LogcatAnalyzerToolWindow toolWindow;
    private final String TAG;
    private volatile boolean running = true; // Volatile to ensure visibility across threads
    Map<String, Integer> redAPIsCount = new HashMap<>(); // Holds the name of APIs and their counts

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
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getAPICallName(String line) {
        int index = line.indexOf('(');
        if (index < 0) {
            return null;
        }

        int firstComma = line.indexOf(',', index + 1);
        if (firstComma < 0) {
            return null;
        }

        return line.substring(index + 1, firstComma).trim();

    }

//    public void analyzeFile(String filePath) {
//        StringBuilder text = new StringBuilder();
//        Map<String, Integer> logLevelCount = new HashMap<>();
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                text.append(line).append("\n");
//
//                String logLevel = getLogLevel(line);
//                logLevelCount.put(logLevel, logLevelCount.getOrDefault(logLevel, 0) + 1);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        toolWindow.updateText(text.toString());
//        updateGraph(logLevelCount);
//    }

    public void stop(){
        running = false;
        //toolWindow.deleteContent();
    }

    private String getLogLevel(String logLine) {
        if (logLine.contains(" E/")) {
            return "ERROR";
        } else if (logLine.contains(" W/")) {
            return "WARN";
        } else if (logLine.contains(" I/")) {
            return "INFO";
        } else if (logLine.contains(" D/")) {
            return "DEBUG";
        } else if (logLine.contains(" V/")) {
            return "VERBOSE";
        }
        return "UNKNOWN";
    }

    private void updateGraph(Map<String, Integer> logLevelCount) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Integer> entry : logLevelCount.entrySet()) {
            dataset.addValue(entry.getValue(), entry.getKey(), "");
        }

        toolWindow.updateGraph(dataset);
    }

}
