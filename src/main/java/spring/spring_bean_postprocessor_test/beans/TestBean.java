package spring.spring_bean_postprocessor_test.beans;

import org.springframework.stereotype.Component;
import spring.spring_bean_postprocessor_test.interfaces.ITest;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public abstract class TestBean implements ITest {

    public TestBean() {
        System.out.println("TestBean constructor");
    }

    public void test() {
        System.out.println("TestBean test");
        getTestBean2().test();
    }

    @PostConstruct
    public void init() {
        System.out.println("Inside TestBean init method");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Inside TestBean destroy method");
    }

    protected abstract TestBean2 getTestBean2();
}
