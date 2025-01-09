package hello.proxy.pureproxy.concreteproxy;

import hello.proxy.pureproxy.concreteproxy.code.ConcreteClient;
import hello.proxy.pureproxy.concreteproxy.code.ConcreteLogic;
import hello.proxy.pureproxy.concreteproxy.code.TimeProxy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ConcreteProxyTest {

    @Test
    void noProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(concreteLogic);
        client.execute();
    }

    // ConcreteClient는 구체 클래스인 ConcreteLogic을 의존하는데,
    // 그의 자식 클래스인 TimeProxy도 들어갈 수 있다. -> 구체 클래스에서 프록시 적용
    // -> 자바 언어의 다형성에서는 인터페이스나 클래스를 구분하지 않고 모두 적용됨, 해당 타입과 그 하위 타입은 모두 다형성의 대상이 됨.

    // 런타임 의존괸계: client -> timeProxy -> concreteLogic

    @Test
    void addProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        TimeProxy timeProxy = new TimeProxy(concreteLogic);
        ConcreteClient client = new ConcreteClient(timeProxy);
        client.execute();
    }

}
