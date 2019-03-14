package spring.spring_bean_postprocessor_test.post;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import spring.spring_bean_postprocessor_test.annotations.CustomInit;
import spring.spring_bean_postprocessor_test.beans.TestBean2;

import java.lang.reflect.Field;
import java.util.Random;

@Component
public class TestBeanPostProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {

        if (bean instanceof TestBean2) {
            System.out.println("Inside post process TestBean2 before initialization: " + beanName);

            Class<?> clazz = bean.getClass();
            for (Field declaredField : clazz.getDeclaredFields()) {
                CustomInit annotation = declaredField.getAnnotation(CustomInit.class);
                if (annotation != null) {
                    ReflectionUtils.makeAccessible(declaredField);
                    ReflectionUtils.setField(declaredField, bean, new Random().ints(annotation.valueMin(),
                            annotation.valueMax()).findAny().getAsInt());
                }
            }
        }

        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {

        if (bean instanceof TestBean2) {
            System.out.println("Inside post process TestBean2 after initialization: " + beanName);
        }

        return bean;
    }


}

