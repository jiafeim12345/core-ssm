package me.cloudcat.develop.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrintLog {
  String name() default "admin";
  boolean showName() default false;
  String content();
}
