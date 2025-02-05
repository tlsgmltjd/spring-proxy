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

    // post processor 에는 스프링이 등록하는 모든 빈이 적용 대상이 되기 때문에 package 로 적용 대상을 제한한다.
    // 이제 프록시 객체를 빈에 수동으로 등록하지 않아도 된다.
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
