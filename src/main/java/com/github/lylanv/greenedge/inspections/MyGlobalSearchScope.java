package com.github.lylanv.greenedge.inspections;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MyGlobalSearchScope extends GlobalSearchScope {

    private MyProjectFileIndex fileIndex;

    public MyGlobalSearchScope(@NotNull Project project) {
        super(project);
        this.fileIndex = (MyProjectFileIndex) ProjectRootManager.getInstance(project).getFileIndex();
        System.out.println("[MyGlobalSearchScope -> " + fileIndex);
    }

    @Override
    public boolean isSearchInModuleContent(@NotNull Module aModule) {
        return false;
    }

    @Override
    public boolean isSearchInLibraries() {
        return false;
    }

    @Override
    public boolean contains(@NotNull VirtualFile file) {
        return false;
//        System.out.println("[MyGlobalSearchScope contains -> " + fileIndex);
//        //return  (fileIndex.isAndroidTestFile(file) || fileIndex.isInLibraryClasses(file));
//        return  (fileIndex.isTestFile(file) || fileIndex.isInLibraryClasses(file));
    }



}
