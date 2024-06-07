package com.github.lylanv.greenedge.inspections;

import com.intellij.openapi.project.Project;

public class Singleton {
    public static Project project;


    public Singleton(final Project project) {
        Singleton.project = project;
    }


    public void setProject(final Project project) {
        this.project = project;
    }

    public Project getProject(){
        return project;
    }
}
