package hello.proxy.jdkdynamic;

import hello.proxy.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

// JDK동적프록시: 인터페이스에만 적용 시킬 수 있음.
// 동적 프록시로 각각의 클래스에 맞는 프록시를 만들지 않아도 하나의 공통 로직(TimeInvocationHandler)으로 여러 메서드에 동적으로 프록시를 생성하고 적용시켜준다.
// -> 프록시 클래스를 수 없이 만들어야하는 문제도 해결하며 부과 기능들을 하나의 클래스에 관리할 수 있기 때문에 SRP 지킴

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void dynamicA() {
        AInterface target = new AImpl();

        TimeInvocationHandler handler = new TimeInvocationHandler(target);
        AInterface proxy = (AInterface) Proxy.newProxyInstance(
                AInterface.class.getClassLoader(),
                new Class[]{AInterface.class}, // 프록시를 사용할 로직 인터페이스 배열
                handler // 적용할 공통 로직
        );

        log.info(String.valueOf(target.getClass()));
        log.info(String.valueOf(proxy.getClass()));

        proxy.call();

    }

    @Test
    void dynamicB() {
        BInterface target = new BImpl();

        TimeInvocationHandler handler = new TimeInvocationHandler(target);
        BInterface proxy = (BInterface) Proxy.newProxyInstance(
                BInterface.class.getClassLoader(),
                new Class[]{BInterface.class}, // 프록시를 사용할 로직 인터페이스 배열
                handler // 적용할 공통 로직
        );

        log.info(String.valueOf(target.getClass()));
        log.info(String.valueOf(proxy.getClass()));

        proxy.call();

    }

}
