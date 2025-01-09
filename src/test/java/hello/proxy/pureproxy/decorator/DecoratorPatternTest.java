package hello.proxy.pureproxy.decorator;

import hello.proxy.pureproxy.decorator.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {

    @Test
    void noDecorator() {
        Component realComponent = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(realComponent);
        client.execute();
    }

    // client 코드 수정 없이 부가 기능을 추가함 (MessageDecorator)
    @Test
    void decorator1() {
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        DecoratorPatternClient client = new DecoratorPatternClient(messageDecorator);
        client.execute();
    }

    // TimeDecorator를 추가하여 실행시간 측정 부가기능을 추가함. proxy 체인을 구현함. + client는 코드를 전혀 변경하지 않음
    // client -> timeDecorator -> messageDecorator -> realComponent
    @Test
    void decorator2() {
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        Component timeDecorator = new TimeDecorator(messageDecorator);
        DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);
        client.execute();
    }


    @Test
    void decorator3() {
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator2(realComponent);
        Component timeDecorator = new TimeDecorator2(messageDecorator);
        DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);
        client.execute();
    }

}
