package hello.proxy.config.v1_proxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// V1 프록시를 추가 클래스 의존 관계
// client -> OrderControllerV1 <<Proxy>>OrderControllerProxy, OrderControllerV1Impl -> OrderServiceV1<<Proxy>>OrderServiceProxy, OrderServiceV1Impl
// V1 프록시를 추가한 런타임 의존 관계
// client -> <<Proxy>>OrderControllerProxy -> OrderControllerV1Impl -> <<Proxy>>OrderServiceProxy -> OrderServiceImpl

// 이렇게 원본 코드, client 코드를 전혀 손대지 않고 인터페이스에 프록시를 적용했음.

@Configuration
public class InterfaceProxyConfig {

    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {
        OrderControllerV1Impl orderControllerImpl = new OrderControllerV1Impl(orderService(logTrace));
        return new OrderControllerInterfaceProxy(orderControllerImpl, logTrace);
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceV1Impl orderServiceImpl = new OrderServiceV1Impl(orderRepository(logTrace));
        return new OrderServiceInterfaceProxy(orderServiceImpl, logTrace);
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1Impl orderRepositoryImpl = new OrderRepositoryV1Impl();
        return new OrderRepositoryInterfaceProxy(orderRepositoryImpl, logTrace);
    }

}
