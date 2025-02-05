package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanPostProcessorTest {

    @Test
    void basicConfig() {
        // 2. beanA 이름으로 B 인스턴스가 빈으로 등록
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class);

        B aButB = annotationConfigApplicationContext.getBean("beanA", B.class);
        aButB.helloB();
    }

    @Slf4j
    @Configuration
    static class BeanPostProcessorConfig {
        @Bean(name = "beanA")
        public A a() {
            // 1. 객체 생성
            return new A();
        }

        @Bean
        public AToBPostProcessor aToBPostProcessor() {
            return new AToBPostProcessor();
        }
    }

    @Slf4j
    static class A {
        public void helloA() {
            log.info("hello A");
        }
    }

    @Slf4j
    static class B {
        public void helloB() {
            log.info("hello B");
        }
    }

    // 빈 후처리기는 빈을 조작하고 변경할 수 있는 후킹 포인트이다.
    // 일반적으로 스프링 컨테이너가 컴포넌트 스캔으로 등록되는 빈은 조작할 방법이 없었는데
    // -> 빈 후처리기로 빈 객체를 프록시 객체로 교체하는 것도 가능하다.
    @Slf4j
    static class AToBPostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName = {}, bean = {}", beanName, bean);
            // beanName = beanA, bean = hello.proxy.postprocessor.BeanPostProcessorTest$A@61861a29
            // name은 beanA지만 B 인스턴스가 빈으로 등록됨.

            if (bean instanceof A) {
                return new B();
            }

            return bean;
        }
    }

}
