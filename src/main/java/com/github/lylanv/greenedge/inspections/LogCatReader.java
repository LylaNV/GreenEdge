package com.github.lylanv.greenedge.inspections;

import com.android.tools.idea.adb.AdbShellCommandsUtil;
import org.jetbrains.kotlin.serialization.js.ast.JsAstProtoBuf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogCatReader implements Runnable {
    private LogcatAnalyzerToolWindow toolWindow;
    private final String TAG;
    private volatile boolean running = true; // Volatile to ensure visibility across threads


    public LogCatReader(LogcatAnalyzerToolWindow toolWindow, String TAG) {
        this.TAG = TAG;
        this.toolWindow = toolWindow;
    }

    @Override
    public void run() {
        try {
            //Process logcatProcess = Runtime.getRuntime().exec("logcat -v time");
            Process logcatProcess = Runtime.getRuntime().exec("adb logcat");
            BufferedReader logcatReader = new BufferedReader(new InputStreamReader(logcatProcess.getInputStream()));
            String line;
            while ((line = logcatReader.readLine()) != null && running) {
                if (line.contains(TAG)) {
                    //logcatOutput.append(line).append("\n");
                    toolWindow.appendLog(line);
                }
            }
            logcatReader.close();
            logcatProcess.destroy();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void stop(){
        running = false;
        toolWindow.deleteContent();
    }

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
}
