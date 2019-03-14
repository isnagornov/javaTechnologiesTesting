package spring.spring_bean_postprocessor_test.beans;

import org.springframework.stereotype.Component;
import spring.spring_bean_postprocessor_test.annotations.CustomInit;
import spring.spring_bean_postprocessor_test.interfaces.ITest;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class TestBean2 implements ITest {

    @CustomInit(valueMin = 10, valueMax = 15)
    private Integer value = 1;

    public TestBean2() {
        System.out.println("TestBean2 constructor");
    }

    public void test() {
        System.out.println(this + " TestBean2 test, currentValue - " + value);
    }

    @PostConstruct
    public void init() {
        System.out.println("Inside TestBean2 init method");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Inside TestBean2 destroy method");
    }
}
