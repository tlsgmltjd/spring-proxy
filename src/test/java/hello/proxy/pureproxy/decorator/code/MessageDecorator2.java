package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator2 extends Decorator {

    public MessageDecorator2(Component component) {
        super(component);
    }

    @Override
    public String operation() {
        log.info("MessageDecorator operation");

        // real component 실행
        String operation = super.operation();
        String decoratedOperation = "*********" + operation + "*********";
        log.info("적용 전: {}, 적용 후: {}", operation, decoratedOperation);
        return decoratedOperation;
    }
}
