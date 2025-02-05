package hello.proxy.config.v5_autoproxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

    // 이렇게 어드바이저만 스프링 빈에 등록하면, 자동 프록시 생성기가 빈으로 등록된 [어드바이저의 포인트 컷으로 프록시를 적용할 객체를 찾는다.]
    // -> 1. 프록시 적용 여부 판단 역할 (생성 단계), 포인트 컷의 클래스 + 메서드 조건을 모두 비교하여 모든 메서드를 체크한다. 조건에 맞는 것이 하나라도 있다면 프록시 적용 대상이다.
    // 프록시 적용 대상의 객체는 프록시 팩토리로 어드바이저를 주입한 프록시 객체로 만들어져 스프링 빈에 자동으로 등록되게 된다.
    // 프록시가 호출 되었을 때 부가 기능이 적용되어야 할 메서드가 호출되었다면. 포인트 컷으로 판단하여 어드바이스를 적용한다.
    // -> 2. 어드바이스 적용 여부 판단 (사용 단계)

    @Bean
    public Advisor getAdvisor(LogTrace logTrace) {
        // pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }

}
