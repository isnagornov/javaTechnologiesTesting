package spring.spring_bean_postprocessor_test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        for (int i = 0; i < 5; i++) {
            Object contextBean = applicationContext.getBean("testBean");

            contextBean.getClass().getMethod("test").invoke(contextBean);
        }

        applicationContext.registerShutdownHook();


    }
}
