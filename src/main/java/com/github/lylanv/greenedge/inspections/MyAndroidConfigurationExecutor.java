package com.github.lylanv.greenedge.inspections;

import com.android.tools.idea.run.configuration.execution.AndroidConfigurationExecutor;
import com.android.tools.idea.run.editor.DeployTarget;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.concurrency.Promise;

public class MyAndroidConfigurationExecutor implements AndroidConfigurationExecutor {

    @NotNull
    @Override
    public RunConfiguration getConfiguration() {
        return null;
    }

    @NotNull
    @Override
    public DeployTarget getDeployTarget() {
        return null;
    }

    @NotNull
    @Override
    public Promise<RunContentDescriptor> run() {
        RunConfiguration runConfiguration = getConfiguration();
        Project project = runConfiguration.getProject();
        System.out.println("MyAndroidConfigurationExecutor.run -> project " + project.getName());
        return null;
    }

    @NotNull
    @Override
    public Promise<RunContentDescriptor> runAsInstantApp() {
        return null;
    }

    @NotNull
    @Override
    public Promise<RunContentDescriptor> debug() {
        return null;
    }

    @NotNull
    @Override
    public Promise<RunContentDescriptor> applyChanges() {
        return null;
    }

    @NotNull
    @Override
    public Promise<RunContentDescriptor> applyCodeChanges() {
        return null;
    }
}
