package hello.proxy.advisor;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Method;

class AdvisorTest {

    @Test
    void advisorTest1() {
        ServiceImpl service = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(service);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.find();
        proxy.save();
    }

    @Test
    @DisplayName("내가만튼포인트컷~")
    void advisorTest2() {
        ServiceImpl service = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(service);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointCut(), new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.find();
        proxy.save();
    }

    // spring이 제공하는 포인트 컷
    // NameMatchMethodPointcut, JdkRegexpMethodPointcut, AnnotationPointcut, *AspectJExpressionPointcut
    @Test
    @DisplayName("provide with spring~")
    void advisorTest3() {
        ServiceImpl service = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(service);
        NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
        nameMatchMethodPointcut.setMappedName("save");
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(nameMatchMethodPointcut, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.find();
        proxy.save();
    }

    static class MyPointCut implements Pointcut {

        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }
    }

    @Slf4j
    static class MyMethodMatcher implements MethodMatcher {

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            String matchName = "save";
            boolean result = method.getName().equals(matchName);

            log.info("포인트 컷 호출 method ={}, targetClass = {}", method.getName(), targetClass);
            log.info("포인트 컷 결과 result = {}", result);

            return result;
        }

        @Override
        public boolean isRuntime() {
            return false;
        }

        // isRuntime()이 true인 경우 실행, 매개변수가 동적으로 들어오기 때문에 내부 캐싱을 사용하기 어렵다
        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return false;
        }
    }

}
