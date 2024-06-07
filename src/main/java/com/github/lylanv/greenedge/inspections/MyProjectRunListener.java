package com.github.lylanv.greenedge.inspections;

import com.intellij.execution.ExecutionListener;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;


public class MyProjectRunListener implements Runnable {
    @Override
    public void run() {
        Project project = ProjectManager.getInstance().getDefaultProject();
    }
}
