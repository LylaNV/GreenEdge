package com.github.lylanv.greenedge.inspections;

import com.android.ddmlib.IDevice;
import com.android.tools.idea.run.AndroidProgramRunner;
import com.android.tools.idea.run.AndroidExecutionState;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import com.github.lylanv.greenedge.inspections.Singleton;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class MyRunListener extends AndroidProgramRunner {

    public MyRunListener() {
        System.out.println("MyRunListener");
        applicationStarted(Singleton.project);
    }

    private void applicationStarted(Project project) {

        for (int i = 0; i < 50000; i++) {
            System.out.println("applicationStarted-------------------Huuuuuuuuraaaaaaaaaaaaaa");
        }

//        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
//        ToolWindow window = toolWindowManager.getToolWindow("myToolWindow");
//        if (window != null) {
//            window.activate(null); // Activate the tool window
//        }
    }

//    @Override
//    public @Nullable Collection<IDevice> getDevices() {
//        applicationStarted(Singleton.project);
//        return List.of();
//    }
//
//    @Override
//    public @Nullable ConsoleView getConsoleView() {
//        return null;
//    }
//
//    @Override
//    public int getRunConfigurationId() {
//        return 0;
//    }
//
//    @Override
//    public @NotNull String getRunConfigurationTypeId() {
//        return "";
//    }

    @Override
    protected boolean canRunWithMultipleDevices(@NotNull String executorId) {
        return false;
    }

    @Override
    public @NotNull @NonNls String getRunnerId() {
        return "";
    }
}
