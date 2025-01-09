package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
class ReflectionTest {

    @Test
    void reflection0() {
        Hello target = new Hello();

        // 공통 로직1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result:{}", result1);
        // 공통 로직1 종료

        // 공통 로직2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result:{}", result2);
        // 공통 로직2 종료
    }

    @Test
    void reflection1() throws Exception {
        // 클래스 정보
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        // callA 매서드 정보
        Method methodCallA = classHello.getMethod("callA");
        // 메서드 메타정보로 타깃 인스턴스의 메서드를 실제로 호출할 수 있음
        Object result1 = methodCallA.invoke(target);
        log.info("result1={}", result1);

        // callB 매서드 정보
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result2={}", result2);

        // 리플랙션의 핵심: 클래스나 메서드 정보를 동적으로 변경할 수 있음.
    }

    @Test
    void reflection2() throws Exception {
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    // dynamicCall는 callA, callB를 한번에 처리할 수 있는 공통 처리 로직
    // 1. 호출할 메서드, 2. 실제 실행할 인스턴스
    // callA, callB를 Method라는 메타 정보로 추상화함. 덕분에 공통 로직을 만들 수 있게 됨
    // -> 리플랙션은 런타임에 동적으로 클래스, 메서드 메타정보를 가져오기 때문에 문제 잡기가 힘들다. 앵간하면 쓰지말자
    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("start");
        Object result1 = method.invoke(target);
        log.info("result:{}", result1);
    }

    @Slf4j
    static class Hello {

        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }

}
