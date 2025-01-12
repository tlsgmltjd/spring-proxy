package hello.proxy.cglib;

import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
class CglibTest {

    @Test
    void cglib() {
        ConcreteService target = new ConcreteService();

        Enhancer enhancer = new Enhancer();
        // cglib: 구체 클래스 기반(ConcreteService)으로 상속 받아 동적으로 프록시 객체를 만듬
        enhancer.setSuperclass(ConcreteService.class);
        enhancer.setCallback(new TimeMethodInterceptor(target));
        ConcreteService proxy = (ConcreteService) enhancer.create();

        log.info("targetClass={}", target.getClass()); // targetClass=class hello.proxy.common.service.ConcreteService
        log.info("proxyClass={}", proxy.getClass()); // class hello.proxy.common.service.ConcreteService$$EnhancerByCGLIB$$48bd19d7

        proxy.call();
    }

}
