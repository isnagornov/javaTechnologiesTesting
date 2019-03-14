package spring.spring_bean_postprocessor_test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import spring.spring_bean_postprocessor_test.beans.TestBean;
import spring.spring_bean_postprocessor_test.beans.TestBean2;

@Configuration
@ComponentScan({"spring.spring_bean_postprocessor_test"})
public class AppConfig {

    @Bean
    public TestBean testBean() {
        return new TestBean() {
            protected TestBean2 getTestBean2() {
                return testBean2();
            }
        };
    }

    @Bean
    @Scope("prototype")
    public TestBean2 testBean2() {
        return new TestBean2();
    }
}
