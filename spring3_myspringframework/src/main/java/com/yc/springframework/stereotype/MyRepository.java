package com.yc.springframework.stereotype;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface MyRepository {
    String value() default "";
}
