package com.github.lylanv.greenedge.inspections;

import com.github.lylanv.greenedge.events.ApplicationStoppedEvent;
import com.github.lylanv.greenedge.events.BuildSuccessEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.android.ddmlib.logcat.LogCatListener;
import com.android.ddmlib.logcat.LogCatMessage;
import com.google.common.eventbus.Subscribe;
import org.gradle.launcher.daemon.protocol.Stop;
import org.jfree.data.category.DefaultCategoryDataset;

public class LogCatReader implements Runnable {
    private LogcatAnalyzerToolWindow toolWindow;
    private final String TAG;
    private volatile boolean running = true; // Volatile to ensure visibility across threads
    Map<String, Integer> redAPIsCount = new HashMap<>(); // Holds the name of APIs and their counts
    private DefaultCategoryDataset dataset;

    public static int energyConsumption = 0;

    private boolean buildSucceeded = false;


    public LogCatReader(LogcatAnalyzerToolWindow toolWindow, String TAG) {
        this.toolWindow = toolWindow;
        this.TAG = TAG;
    }

    @Subscribe
    public void handleBuildSuccessEvent(BuildSuccessEvent event) {
        if (event.getBuildStatus()){
            buildSucceeded = true;
            System.out.println("[LogCatReader -> handleBuildSuccessEvent$ Build successful");
        }
    }

    @Subscribe
    public void handleApplicationStoppedEvent(ApplicationStoppedEvent event) throws IOException {
        if (event.getApplicationStopped()){
            System.out.println("[LogCatReader -> handleBuildSuccessEvent$ Application stopped");
            stop();
        }
    }

    @Override
    public void run() {
        try {
            //toolWindow.drawLineGraph();
            Process logcatCleaningProcess = Runtime.getRuntime().exec("adb logcat -c");
            Process logcatProcess = Runtime.getRuntime().exec("adb logcat");
            BufferedReader logcatReader = new BufferedReader(new InputStreamReader(logcatProcess.getInputStream()));
            String line;
            while ((line = logcatReader.readLine()) != null && running) {
                if (line.contains(TAG)) {
                    toolWindow.appendLog(line);

                    String extractedAPICallName = getAPICallName(line);
                    redAPIsCount.put(extractedAPICallName, redAPIsCount.getOrDefault(extractedAPICallName, 0) + 1);
                    energyConsumption ++;
                    updateGraph(redAPIsCount);
                } else {
                    // TODO: I got null pointer- Check it
                    if (line.contains(GreenMeter.projectName)) {
                        System.out.println(line);
                    }
                }
            }
            logcatReader.close();
            logcatProcess.destroy();
            //stop();
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
        running = false;
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("adb logcat -c");
        if (dataset !=null){
            dataset.clear();
        }
        redAPIsCount.clear();
        energyConsumption = 0;
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
