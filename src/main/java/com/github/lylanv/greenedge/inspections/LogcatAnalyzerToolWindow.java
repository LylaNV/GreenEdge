package com.github.lylanv.greenedge.inspections;

import javax.swing.*;
import java.awt.*;

public class LogcatAnalyzerToolWindow {
    private JPanel myToolWindowContent;
    private JTextArea logTextArea;

    public LogcatAnalyzerToolWindow() {
        logTextArea = new JTextArea();
        myToolWindowContent = new JPanel();
        myToolWindowContent.setLayout(new BorderLayout());
        myToolWindowContent.add(new JScrollPane(logTextArea), BorderLayout.CENTER);
    }

    public JPanel getContent() {
        return myToolWindowContent;
    }

    public void deleteContent() {
        logTextArea.setText("");
    }

    public void appendLog(String log) {
        logTextArea.append(log + "\n");
    }
}
