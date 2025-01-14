package hello.proxy;

import hello.proxy.config.AppV2Config;
import hello.proxy.config.v2_dynamicproxy.DynamicProxyBasicConfig;
import hello.proxy.config.v2_dynamicproxy.DynamicProxyFilterConfig;
import hello.proxy.config.v3_proxyfactory.ProxyFactoryConfigV1;
import hello.proxy.config.v3_proxyfactory.ProxyFactoryConfigV2;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

// 요구사항: 로그 추적기를 원본 코드 수정 없이 적용해야함. 특정 메서드에 적용할 수도 있어야함. 인터페이스, 구현클래스, 반 수동등록, 자동등록과 같은 여러 케이스에 모두 도입할 수 있어야함
// v1: interface + impl 빈 수동 등록
// v2: impl 빈 수동 등록
// v3: impl 빈 자동 등록

//@Import(AppV1Config.class)
//@Import({AppV1Config.class, AppV2Config.class})
//@Import({ConcreteProxyConfig.class})
//@Import({DynamicProxyBasicConfig.class, AppV2Config.class})
//@Import({DynamicProxyFilterConfig.class, AppV2Config.class})
//@Import({ProxyFactoryConfigV1.class, AppV2Config.class})
@Import({ProxyFactoryConfigV2.class})
@SpringBootApplication(scanBasePackages = "hello.proxy.app") //주의
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

	@Bean
	public LogTrace logTrace() {
		return new ThreadLocalLogTrace();
	}

}
