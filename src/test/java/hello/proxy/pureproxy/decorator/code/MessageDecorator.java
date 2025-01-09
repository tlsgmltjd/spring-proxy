package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

// 데코레이터가 응답 값을 꾸며서 클라이언트에게 반환해줌
@Slf4j
public class MessageDecorator implements Component {

    private Component component;

    public MessageDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("MessageDecorator operation");

        // real component 실행
        String operation = component.operation();
        String decoratedOperation = "*********" + operation + "*********";
        log.info("적용 전: {}, 적용 후: {}", operation, decoratedOperation);
        return decoratedOperation;
    }
}
