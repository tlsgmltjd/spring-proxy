package hello.proxy.config.v1_proxy;

import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.config.v1_proxy.contrete_proxy.OrderControllerConcreteProxy;
import hello.proxy.config.v1_proxy.contrete_proxy.OrderRepositoryConcreteProxy;
import hello.proxy.config.v1_proxy.contrete_proxy.OrderServiceConcreteProxy;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 구현체 클래스 기반으로 프록시 기능 적용: example v2

@Configuration
public class ConcreteProxyConfig {

    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
        OrderRepositoryV2 orderRepositoryImpl = new OrderRepositoryV2();
        return new OrderRepositoryConcreteProxy(orderRepositoryImpl, logTrace);
    }

    @Bean
    public OrderServiceV2 orderServiceV2(LogTrace logTrace) {
        OrderServiceV2 orderServiceImpl = new OrderServiceV2(orderRepositoryV2(logTrace));
        return new OrderServiceConcreteProxy(orderServiceImpl, logTrace);
    }

    @Bean
    public OrderControllerV2 orderControllerV2(LogTrace logTrace) {
        OrderControllerV2 orderControllerImpl = new OrderControllerV2(orderServiceV2(logTrace));
        return new OrderControllerConcreteProxy(orderControllerImpl, logTrace);
    }

}
