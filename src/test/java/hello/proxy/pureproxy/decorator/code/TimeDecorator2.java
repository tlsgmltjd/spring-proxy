package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeDecorator2 extends Decorator {

    public TimeDecorator2(Component component) {
        super(component);
    }

    @Override
    public String operation() {
        log.info("TimeDecorator operation");
        long startTime = System.currentTimeMillis();
        String result = super.operation();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeDecorator operation cost time: {} ms", resultTime);
        return result;
    }
}
