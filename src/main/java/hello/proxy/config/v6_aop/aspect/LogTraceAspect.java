package hello.proxy.config.v6_aop.aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j

// ++ 추가정보: 스프링은 빈이 등록될 때 해당 빈이 프록시 적용 대상인지 확인하기 위해 빈 후처리기와 비교 대상인 어드바이저 빈 안에 있는 포인트 컷을 확인해야한다.
// 그렇다면 어드바이저 빈은 다른 빈 객체보다 우선 등록 되어야 하는데, 스프링 내부에서 빈 등록 우선순위를 두어 이 문제를 해결한다.

// AnnotationAwareAspectJAutoProxyCreator 는 @Aspect 어노테이션을 찾아 Advior로 만들어준다.
// 이 자동 프록시 생성기는 @Aspect 어노테이션이 붙은 스프링 빈을 어드바이저로 변환하여 저장하고, 어드바이저 기반으로 프록시 객체를 생성하는 두가지 일을 한다.
// -> 이렇게 만들어잔 어드바이저들은 내부 어드바이저 빌더에 저장 해두고 자동 프록시 생성기가 스프링 빈이 등록 될 때 스플이 컨테이너에서 등록되어있는 어드바이저 빈을 조회해서 포인트컷을 확인 할때 같이 확인된다.
@Aspect // 어노테이션 기반 프록시를 적용할 때 사용한다.
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    // @Around: 값이 포인트컷 표현식을 넣는다 AspectJ, 해당 어노테이샨의 메서드는 Advice가 된다. joinPoint는 실제 호출 대상이 되는 타켓 메서드이다.
    @Around("execution(* hello.proxy.app..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;
        try {
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            // target 호출
            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }

}
