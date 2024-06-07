package com.github.lylanv.greenedge.inspections;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.ReadonlyStatusHandler;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

public class MethodCallAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project != null) {
            PsiClass logClass = JavaPsiFacade.getInstance(project).
                    findClass("android.util.Log", GlobalSearchScope.allScope(project));

            PsiFile file = e.getData(CommonDataKeys.PSI_FILE);
            if (file != null) {
                file.accept(new JavaRecursiveElementWalkingVisitor() {
                    @Override
                    public void visitMethodCallExpression(@NotNull PsiMethodCallExpression expression) {
                        super.visitMethodCallExpression(expression);
                        PsiExpressionList argumentList = expression.getArgumentList();
                        if (argumentList != null) {
                            PsiExpression[] arguments = argumentList.getExpressions();
                            if (arguments.length > 0) {
                                PsiExpression firstArgument = arguments[0];
                                String logTag = firstArgument.getText();
                                String logMessage = expression.getMethodExpression().getReferenceName() + " called";
                                //String logStatment = "Log.d(" + logTag + ", \"" + logMessage + "\");";
                                String logStatment = "System.out.println(\"Method is called\");"+ "\n";

                                PsiElementFactory factory = JavaPsiFacade.getInstance(project).getElementFactory();
                                //PsiStatement statement = factory.createStatementFromText(logStatment, expression.getContext());
                                PsiStatement statement = factory.createStatementFromText(logStatment, null);
                                WriteCommandAction.runWriteCommandAction(project, (Runnable) () -> {expression.getParent().addBefore(statement,expression);});
                                //expression.addBefore(statement, expression);
                                //expression.getParent().addBefore(statement, expression);

                                PsiFile methodFile = firstArgument.getContainingFile();

                                System.out.println(logStatment);
                                System.out.println(methodFile);
                            }
                        }
                    }
                });
            }
        }
    }
}
