package spring.spring_bean_postprocessor_test.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CustomInit {

    int valueMin();

    int valueMax();
}
