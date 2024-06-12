package com.github.lylanv.greenedge.inspections;

import com.android.tools.idea.projectsystem.ProjectSystemBuildManager;
import com.android.tools.idea.projectsystem.ProjectSystemBuildManager.BuildListener;
import com.esotericsoftware.kryo.NotNull;
import com.intellij.build.events.BuildEvent;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.util.indexing.FileBasedIndex;
import com.github.lylanv.greenedge.inspections.LogcatAnalyzerToolWindow;

import java.util.Collection;


public class MyCompiler implements BuildListener {

    public final String Logging_TAG = "MethodCallProfiler";

    public MyCompiler() {
        this.buildStarted(ProjectSystemBuildManager.BuildMode.COMPILE);
    }

    @Override
    public void buildStarted(final ProjectSystemBuildManager.BuildMode buildMode) {
        System.out.println("buildStarted " + buildMode.name());
        if (buildMode == ProjectSystemBuildManager.BuildMode.COMPILE) {
            MyInspection();
        }
    }

    private void MyInspection(){

        System.out.println("Run button clicked");
        //LogcatAnalyzerToolWindowFactory logcatAnalyzerToolWindowFactory = new LogcatAnalyzerToolWindowFactory();


        Project project = ProjectManager.getInstance().getDefaultProject();

        if (project == null) {
            System.out.println("There is not any project");
            return;
        } else {
            int psiClassNamesNumber = PsiShortNamesCache.getInstance(project).getAllClassNames().length;
            System.out.println("psiClassNamesNumber: " + psiClassNamesNumber);

//            //https://intellij-support.jetbrains.com/hc/en-us/community/posts/360009512280-Find-all-PsiClasses-in-Project
//            Collection<VirtualFile> containingFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME,
//                    JavaFileType.INSTANCE,
//                    MyGlobalSearchScope.projectScope(project));
            //https://intellij-support.jetbrains.com/hc/en-us/community/posts/360009512280-Find-all-PsiClasses-in-Project
            Collection<VirtualFile> containingFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME,
                    JavaFileType.INSTANCE,
                    GlobalSearchScope.projectScope(project));
            System.out.println("containing Files Number: " + containingFiles.toArray().length);
            System.out.println("containing Files: " + containingFiles);

//            VirtualFile virtualFile = psiFile.getVirtualFile();
//
//            if (ReadonlyStatusHandler.ensureFilesWritable(project, virtualFile)){
//                System.out.println("ReadonlyStatusHandler -> We can write the files!");
//                System.out.println("ReadonlyStatusHandler -> virtual file is: " + virtualFile);
//            }else {
//                System.out.println("ReadonlyStatusHandler -> Files are read only!");
//            }

//            PsiClass[][] psiClasses = getAllClassesOfProject(project);
//
//            for (int i = 0; i < psiClassNamesNumber; i++) {
//                for (PsiClass psiClass : psiClasses[i]) {
//                    PsiMethod[] methods = psiClass.getMethods();
//                    for (PsiMethod method : methods) {}
//
//                    PsiClass[] superClasses = Psi
//                }
//            }

//            for (VirtualFile virtualFile : containingFiles) {
//                /*
//                 * By this if we exclude all java files that are not in the main folder of the project such as test files
//                 * to be more precise androidTest and test
//                 * */
//                if (virtualFile.getUrl().contains("/src/main")){
//                    //System.out.println("[MyAnnotator -> actionPerformed $ MAIN CLASS " );
//                    PsiClass[] psiClass = convertVirtualFileToPsiClass(project,virtualFile);
//                    if (psiClass.length == 0) {
//                        System.out.println("[MyAnnotator -> actionPerformed $ psiClass is null");
//                    }else {
//                        for (PsiClass psiClass1 : psiClass) {
//                            PsiMethod[] psiMethods = psiClass1.getMethods();
//                            for (PsiMethod psiMethod : psiMethods) {
//                                System.out.println(psiMethod);
//                                PsiElementFactory factory = JavaPsiFacade.getElementFactory(psiMethod.getProject());
//                                PsiAnnotation annotation = factory.createAnnotationFromText("@StartOfMethod", psiMethod);
//                                //psiMethod.getModifierList().addBefore(annotation, psiMethod.getFirstChild());
////                               if (psiMethod.getFirstChild() == null || psiMethod.getFirstChild() == psiMethod.getParent()){
////                                   System.out.println("[MyAnnotator -> actionPerformed $ getFirstChild is null or parent");
////                                   WriteCommandAction.runWriteCommandAction(project, (Runnable) () -> {psiMethod.getModifierList().addAfter(annotation, psiMethod.getParent());});
////                               } else {
////                                   WriteCommandAction.runWriteCommandAction(project, (Runnable) () -> {psiMethod.getModifierList().addBefore(annotation, psiMethod.getFirstChild());});
////                               }
//
//                                new WriteCommandAction.Simple(project, psiMethod.getContainingFile()) {
//                                    @Override
//                                    protected void run() throws Throwable {
//                                        PsiModifierList modifierList = psiMethod.getModifierList();
//                                        PsiElement firstChild = modifierList.getFirstChild();
//
//                                        if (modifierList != null) {
//
//                                            PsiAnnotation[] annotations = modifierList.getAnnotations();
//
//                                            if (annotations.length > 0) {
//                                                // Find the first child of modifierList that is not an annotation
//                                                PsiElement insertionPoint = null;
//                                                PsiElement[] children = modifierList.getChildren();
//                                                for (PsiElement child : children) {
//                                                    if (!(child instanceof PsiAnnotation)) {
//                                                        insertionPoint = child;
//                                                        break;
//                                                    }
//                                                }
//                                                if (insertionPoint != null) {
//                                                    // Add the annotation before the insertion point
//                                                    PsiElement finalInsertionPoint = insertionPoint;
//                                                    WriteCommandAction.runWriteCommandAction(project, (Runnable) () -> {modifierList.addBefore(annotation, finalInsertionPoint);});
//                                                } else {
//                                                    // If all children are annotations, add the annotation at the end of the modifier list
//                                                    WriteCommandAction.runWriteCommandAction(project, (Runnable) () -> {modifierList.add(annotation);});
//                                                }
//                                            } else {
//                                                if (firstChild instanceof PsiKeyword) {
//                                                    // Add the annotation before the "public" modifier
//                                                    WriteCommandAction.runWriteCommandAction(project, (Runnable) () -> {modifierList.addBefore(annotation, firstChild);});
//                                                } else {
//                                                    // If there is no existing modifier or it's not a PsiKeyword, simply add the annotation to the modifier list
//                                                    WriteCommandAction.runWriteCommandAction(project, (Runnable) () -> {modifierList.add(annotation);});
//                                                }
//                                            }
//
////                                           if (firstChild instanceof PsiKeyword) {
////                                               // Add the annotation before the "public" modifier
////                                               WriteCommandAction.runWriteCommandAction(project, (Runnable) () -> {modifierList.addBefore(annotation, firstChild);});
////                                           } else {
////                                               // If there is no existing modifier or it's not a PsiKeyword, simply add the annotation to the modifier list
////                                               WriteCommandAction.runWriteCommandAction(project, (Runnable) () -> {modifierList.add(annotation);});
////                                           }
//
////                                           WriteCommandAction.runWriteCommandAction(project, (Runnable) () -> {modifierList.addBefore(annotation, psiMethod);});
//
////                                           PsiElement firstChild = psiMethod.getFirstChild();
////                                           PsiElement parent = psiMethod.getParent();
////
////                                           if (firstChild == null || firstChild.getParent() == parent) {
////                                               modifierList.addAfter(annotation, psiMethod.getDocComment());
////                                           } else {
////                                               modifierList.addAfter(annotation,firstChild);
////                                           }
////
//////                                           try{
//////                                               if (firstChild == null) {
//////                                                   System.out.println("[MyAnnotator -> actionPerformed $ Assertion failed: anchorBefore == null");
//////                                                   modifierList.addBefore(annotation, psiMethod);
//////                                               }else {
//////                                                   if (firstChild.getPrevSibling() == null) {
//////                                                       System.out.println("[MyAnnotator -> actionPerformed $ Assertion failed: first child parent = " + firstChild);
//////                                                       System.out.println("[MyAnnotator -> actionPerformed $ Assertion failed: anchorBefore.getTreeParent() == parent");
//////                                                       modifierList.addAfter(annotation, psiMethod.getFirstChild());
//////                                                       //modifierList.addBefore(annotation, psiMethod);
//////                                                       System.out.println("[MyAnnotator -> actionPerformed $ Assertion failed: anchorBefore.getTreeParent() == parent" + "psiMethod.getFirstChild() = " + psiMethod.getFirstChild());
//////                                                   }else {
//////                                                       modifierList.addBefore(annotation, firstChild);
//////                                                   }
//////                                               }
//////                                           }catch (AssertionError e) {
//////                                               System.out.println("[MyAnnotator -> actionPerformed $ Assertion failed: psiMethod is null");
//////                                           }
//                                        } else {
//                                            WriteCommandAction.runWriteCommandAction(project, (Runnable) () -> {modifierList.add(annotation);});
//                                        }
//                                    }
//                                }.execute();
//                            }
//                            //System.out.println("[MyAnnotator -> actionPerformed $ psiClass is " + psiClass1.getText());
//                        }
//                    }
//                }
//            }
        }

        System.out.println("YUHOOOOOOOOO project is " + project.getName());
        System.out.println("YUHOOOOOOOOO project is " + project.getBasePath());
    }

}
