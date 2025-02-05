package hello.proxy.config.v4_postprocessor.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Slf4j
public class PackageLogTracePostProcessor implements BeanPostProcessor {

    private final String basePackage;
    private final Advisor advisor;

    // 이제 프록시 객체를 빈에 수동으로 등록하지 않아도 된다.
    // post processor 에는 스프링이 등록하는 모든 빈이 적용 대상이 되기 때문에 package 로 적용 대상을 제한한다.
    // -> 하지만 이렇게 제한하는 방법은 좋은 구조는 아니라고 생각한다. 어드바이저에 포함된 포인트컷을 활용한다면 클래스, 메서드 단위 필터링 기능을 제공하고 있기 때문에 정밀하게 설정이 가능하다.

    // 결과적으로 앞으로 포인트컷은 1. 프록시 적용 객체 여부 판단, 2. 어드바이스를 적용할 메서드 판단 이렇게 두가지 역할을 하게 된다.
    public PackageLogTracePostProcessor(String basePackage, Advisor advisor) {
        this.basePackage = basePackage;
        this.advisor = advisor;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
      log.info("param beanName = {}, bean = {}", beanName, bean.getClass());

      // 프록시 적용 대상 여부 확인
      // 프록시 적용 대상이 아니면 원본 객체 그대로 등록
      String packageName = bean.getClass().getPackageName();
      if (!packageName.startsWith(basePackage)) {
          return bean;
      }

      // 프록시 적용 대상이면 프록시를 만들어서 반환
      ProxyFactory proxyFactory = new ProxyFactory(bean);
      proxyFactory.addAdvisor(advisor);

        Object proxy = proxyFactory.getProxy();
        log.info("create proxy, target = {}, proxy = {}",bean.getClass(), proxy.getClass());
        return proxy;

    }
}
