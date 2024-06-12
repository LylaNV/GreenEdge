package com.github.lylanv.greenedge.inspections;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NotNull;

public class LogcatAnalyzerToolWindowFactory implements ToolWindowFactory, DumbAware {
    private Thread logcatAnalyzerThread;
    private LogCatReader logcatReader;
    private final String Logging_TAG = "MethodCallProfiler";

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
//        LogcatAnalyzerToolWindow logcatAnalyzerToolWindow = new LogcatAnalyzerToolWindow(project);
//        //logcatAnalyzerToolWindow.deleteContent();
//        ContentFactory contentFactory = ContentFactory.getInstance();
//        Content content = contentFactory.createContent(logcatAnalyzerToolWindow.getContent(),"",false);
//        toolWindow.getContentManager().addContent(content);
//
//        logcatReader = new LogCatReader(logcatAnalyzerToolWindow,Logging_TAG);
//        logcatAnalyzerThread = new Thread(logcatReader);
//        logcatAnalyzerThread.start();
//
//        MessageBusConnection connection = project.getMessageBus().connect(project);
//        connection.subscribe(ToolWindowManagerListener.TOPIC, new ToolWindowManagerListener() {
//            @Override
//            public void toolWindowShown(ToolWindow window) {
//                if (window.getId().equals(toolWindow.getId())) {
//                    if (logcatAnalyzerThread == null || !logcatAnalyzerThread.isAlive()) {
//                        logcatReader = new LogCatReader(logcatAnalyzerToolWindow,Logging_TAG);
//                        logcatAnalyzerThread = new Thread(logcatReader);
//                        logcatAnalyzerThread.start();
//                    }
//                }
//            }
//
//            public void toolWindowHidden(ToolWindow window) {
//                if (window.getId().equals(toolWindow.getId())) {
//                    if (logcatAnalyzerThread != null && logcatAnalyzerThread.isAlive()) {
//                        logcatReader.stop();
//                        try {
//                            logcatAnalyzerThread.join();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        logcatAnalyzerThread = null;
//                    }
//                }
//            }
//        });


        LogcatAnalyzerToolWindow logcatAnalyzerToolWindow = new LogcatAnalyzerToolWindow(project);
        ContentFactory contentFactory = ContentFactory.getInstance();

        // Text tab
        Content textContent = contentFactory.createContent(logcatAnalyzerToolWindow.getTextPanel(), "Text", false);
        toolWindow.getContentManager().addContent(textContent);

        // Bar graph tab
        Content barGraphContent = contentFactory.createContent(logcatAnalyzerToolWindow.getGraphPanel(), "Bar Graph", false);
        toolWindow.getContentManager().addContent(barGraphContent);

        logcatReader = new LogCatReader(logcatAnalyzerToolWindow,Logging_TAG);
        logcatAnalyzerThread = new Thread(logcatReader);
        logcatAnalyzerThread.start();

        MessageBusConnection connection = project.getMessageBus().connect(project);
        connection.subscribe(ToolWindowManagerListener.TOPIC, new ToolWindowManagerListener() {
            @Override
            public void toolWindowShown(ToolWindow window) {
                if (window.getId().equals(toolWindow.getId())) {
                    if (logcatAnalyzerThread == null || !logcatAnalyzerThread.isAlive()) {
                        logcatReader = new LogCatReader(logcatAnalyzerToolWindow,Logging_TAG);
                        logcatAnalyzerThread = new Thread(logcatReader);
                        logcatAnalyzerThread.start();
                    }
                }
            }

            public void toolWindowHidden(ToolWindow window) {
                if (window.getId().equals(toolWindow.getId())) {
                    if (logcatAnalyzerThread != null && logcatAnalyzerThread.isAlive()) {
                        logcatReader.stop();
                        try {
                            logcatAnalyzerThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        logcatAnalyzerThread = null;
                    }
                }
            }
        });

    }
}
