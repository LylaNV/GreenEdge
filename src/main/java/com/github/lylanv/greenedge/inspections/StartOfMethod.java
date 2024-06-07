package com.github.lylanv.greenedge.inspections;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/RetentionPolicy.html
@Retention(RetentionPolicy.RUNTIME) // Annotations are to be recorded in the class file by the compiler and retained by the VM at run time, so they may be read reflectively.
@Target(ElementType.METHOD) // Specify where this annotation can be applied (e.g., methods)
public @interface StartOfMethod {
    String value() default "";
}
