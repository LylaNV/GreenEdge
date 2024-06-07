package com.github.lylanv.greenedge.inspections;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiLiteralExpression;

public class MethodLoggingPlugin extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        System.out.println("MethodLoggingPlugin button clicked");
        Project project = event.getData(PlatformDataKeys.PROJECT);
        if (project == null) return;
        System.out.println("Project name is " + project.getName());
        // Get the currently open document
//        Document document = PsiDocumentManager.getInstance(project).getDocument((Editor) event.getData(PlatformDataKeys.EDITOR));
//        if (document == null) return;

        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        if (editor == null) return;
        //System.out.println("Editor name is " + editor.getDocument().getText());

        // Retrieve the PsiFile associated with the editor
        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());
        if (psiFile == null) return;
        System.out.println("PsiFile name is " + psiFile.getName());

        Document document = (Document) PsiDocumentManager.getInstance(project).getDocument(psiFile);
        if (document == null) return;
        //System.out.println("Document name is " + document.getText());

        // Get the root element of the currently open file
        PsiElement psiRootElement = PsiDocumentManager.getInstance(project).getPsiFile(document);
        //PsiElement psiRootElement = PsiDocumentManager.getInstance(project).getPsiFile((Document) psiFile);
        if (psiRootElement == null) return;
        //System.out.println("psiRootElement name is " + psiRootElement.getText());

        // Find all methods in the current file and add logging statements
//        PsiMethod[] methods = PsiTreeUtil.getChildrenOfType(psiRootElement, PsiMethod.class);
//        if (methods != null) {
//            for (PsiMethod method : methods) {
//                System.out.println("Added method " + method.getName());
//                addLoggingStatement(method);
//                System.out.println("Added method " + method.getName() + "done");
//            }
//        }else {
//            System.out.println("No methods found");
//        }


        psiFile.accept(new JavaRecursiveElementWalkingVisitor() {
            @Override
            public void visitMethod(PsiMethod method) {
                System.out.println("A method is visited -> method name is: " + method.getName());
                addLoggingStatement(method);
                System.out.println("A method is visited -> method name is: " + method.getName() + " and a log statement is added");
            }
        });
    }

    private void addLoggingStatement(PsiMethod method) {
        System.out.println("[addLoggingStatement$ Start of addLoggingStatement");
        Project project = method.getProject();
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);

        System.out.println("[addLoggingStatement$ Project name is " + project.getName());
        System.out.println("[addLoggingStatement$ JavaPsiFacade project's name is " + javaPsiFacade.getProject().getName());

        // Get the logger class from the project
        PsiElement loggerElement = javaPsiFacade.findClass("java.util.logging.Logger",
                GlobalSearchScope.allScope(project));
        //System.out.println("[addLoggingStatement$ loggerElement name is " + loggerElement.getText());

        // Check if logger class exists in the project
        if (loggerElement != null) {
            System.out.println("[addLoggingStatement$ start logging statement");
            // Add logging statement to the beginning of the method
            //method.addBefore(createLoggingStatement(method), method.getParent());
            method.getBody().addBefore(createLoggingStatement(method), method.getBody().getFirstChild());
            System.out.println("[addLoggingStatement$ end logging statement");
        }
    }

    private PsiElement createLoggingStatement(PsiMethod method) {
//        Project project = method.getProject();
//        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
//
//        // Create a logging statement using java.util.logging.Logger
//        PsiElement loggerElement = javaPsiFacade.findClass("java.util.logging.Logger",
//                GlobalSearchScope.allScope(project));
//        if (loggerElement != null) {
//            String loggerName = method.getContainingClass().getQualifiedName();
//            String methodName = method.getName();
//
//            String loggingStatement = "Logger.getLogger(" + loggerName + ".class.getName()).info(\"Entering method " + methodName + "\");\n";
//            System.out.println("log added :" + loggingStatement);
//
//            return javaPsiFacade.getElementFactory().createStatementFromText(loggingStatement, null);
//        }
//        return null;

        Project project = method.getProject();
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);

        if (method == null) {return null;}
        else {
            String logMessage = method.getName() + " starts logging statement";
            String logText = "Log.d(" + "\"" + logMessage + "\");";
            return javaPsiFacade.getElementFactory().createStatementFromText(logText, null);
        }
    }
}
