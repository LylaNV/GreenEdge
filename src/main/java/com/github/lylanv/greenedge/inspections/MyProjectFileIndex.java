package com.github.lylanv.greenedge.inspections;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.OrderEntry;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileFilter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.model.module.JpsModuleSourceRootType;

import java.util.List;
import java.util.Set;

public class MyProjectFileIndex implements ProjectFileIndex {
    @Override
    public boolean isInProject(@NotNull VirtualFile file) {
        return false;
    }

    @Override
    public boolean isInProjectOrExcluded(@NotNull VirtualFile file) {
        return false;
    }

    @Override
    public @Nullable Module getModuleForFile(@NotNull VirtualFile file) {
        return null;
    }

    @Override
    public @Nullable Module getModuleForFile(@NotNull VirtualFile file, boolean honorExclusion) {
        return null;
    }

    @Override
    public @NotNull List<OrderEntry> getOrderEntriesForFile(@NotNull VirtualFile file) {
        return List.of();
    }

    @Override
    public @Nullable VirtualFile getClassRootForFile(@NotNull VirtualFile file) {
        return null;
    }

    @Override
    public @Nullable VirtualFile getSourceRootForFile(@NotNull VirtualFile file) {
        return null;
    }

    @Override
    public @Nullable VirtualFile getContentRootForFile(@NotNull VirtualFile file) {
        return null;
    }

    @Override
    public @Nullable VirtualFile getContentRootForFile(@NotNull VirtualFile file, boolean honorExclusion) {
        return null;
    }

    @Override
    public @Nullable String getPackageNameByDirectory(@NotNull VirtualFile dir) {
        return "";
    }

    @Override
    public boolean isLibraryClassFile(@NotNull VirtualFile file) {
        return false;
    }

    @Override
    public boolean isInSource(@NotNull VirtualFile fileOrDir) {
        return false;
    }

    @Override
    public boolean isInLibraryClasses(@NotNull VirtualFile fileOrDir) {
        return false;
    }

    @Override
    public boolean isInLibrary(@NotNull VirtualFile fileOrDir) {
        return false;
    }

    @Override
    public boolean isInLibrarySource(@NotNull VirtualFile fileOrDir) {
        return false;
    }

    @Override
    public boolean isIgnored(@NotNull VirtualFile file) {
        return false;
    }

    @Override
    public boolean isExcluded(@NotNull VirtualFile file) {
        return false;
    }

    @Override
    public boolean isUnderIgnored(@NotNull VirtualFile file) {
        return false;
    }

    @Override
    public @Nullable JpsModuleSourceRootType<?> getContainingSourceRootType(@NotNull VirtualFile file) {
        return null;
    }

    @Override
    public boolean isInGeneratedSources(@NotNull VirtualFile file) {
        return false;
    }

    @Override
    public @Nullable String getUnloadedModuleNameForFile(@NotNull VirtualFile fileOrDir) {
        return "";
    }

    @Override
    public boolean iterateContent(@NotNull ContentIterator processor) {
        return false;
    }

    @Override
    public boolean iterateContent(@NotNull ContentIterator processor, @Nullable VirtualFileFilter filter) {
        return false;
    }

    @Override
    public boolean iterateContentUnderDirectory(@NotNull VirtualFile dir, @NotNull ContentIterator processor) {
        return false;
    }

    @Override
    public boolean iterateContentUnderDirectory(@NotNull VirtualFile dir, @NotNull ContentIterator processor, @Nullable VirtualFileFilter customFilter) {
        return false;
    }

    @Override
    public boolean isInContent(@NotNull VirtualFile fileOrDir) {
        return false;
    }

    @Override
    public boolean isInSourceContent(@NotNull VirtualFile fileOrDir) {
        return false;
    }

    @Override
    public boolean isInTestSourceContent(@NotNull VirtualFile fileOrDir) {
        return false;
    }

    @Override
    public boolean isUnderSourceRootOfType(@NotNull VirtualFile fileOrDir, @NotNull Set<? extends JpsModuleSourceRootType<?>> rootTypes) {
        return false;
    }

    public boolean isAndroidTestFile(@NotNull VirtualFile file) {
        VirtualFile testFile = file.findChild("org.junit.Test");
        System.out.println("[isAndroidTestFile -> $ The input virtual file is  " + testFile);
        System.out.println(testFile);

        if (testFile != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isTestFile(@NotNull VirtualFile file) {
        // Get the path of the file
        String filePath = file.getPath();

        // Define your test directory structure or naming conventions
        String[] testDirectoryKeywords = {"/test/", "/tests/", "/testData/", "/it/", "/integrationTests/", "/unitTests/"};
        String[] testFileSuffixes = {"Test.java", "Tests.java"};

        // Check if the file path contains any of the test directory keywords
        for (String keyword : testDirectoryKeywords) {
            if (filePath.contains(keyword)) {
                return true;
            }
        }

        // Check if the file name ends with any of the test file suffixes
        for (String suffix : testFileSuffixes) {
            if (file.getName().endsWith(suffix)) {
                return true;
            }
        }

        // If none of the above conditions are met, it's not a test file
        return false;
    }

}
