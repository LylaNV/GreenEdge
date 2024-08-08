package com.github.lylanv.greenedge.inspections;

import com.github.lylanv.greenedge.events.BuildSuccessEvent;
import com.android.tools.idea.projectsystem.ProjectSystemBuildManager;
import com.android.tools.idea.projectsystem.ProjectSystemBuildManager.BuildListener;

public class AndroidProjectBuildListener implements BuildListener {

    @Override
    public void buildCompleted(final ProjectSystemBuildManager.BuildResult buildStatus) {
        if (buildStatus.getStatus() == ProjectSystemBuildManager.BuildStatus.SUCCESS){
            System.out.println("Build completed successfully");
            EventBusManager.post(new BuildSuccessEvent(true));
        }
    }

}
