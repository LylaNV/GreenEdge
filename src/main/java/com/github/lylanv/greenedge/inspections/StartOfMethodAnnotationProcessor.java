package com.github.lylanv.greenedge.inspections;

import com.intellij.psi.PsiMethod;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;


@SupportedAnnotationTypes("com.github.lylanv.greenedge.inspection.StartOfMethod")
public class StartOfMethodAnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element element : roundEnv.getElementsAnnotatedWith(StartOfMethod.class)) {
            if (element instanceof PsiMethod) {
                System.out.println("StartOfMethod found ------ WORKED");
            }
        }

        return false;
    }
}
