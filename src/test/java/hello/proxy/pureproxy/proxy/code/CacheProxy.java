package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject {

    private Subject target;
    private String cacheValue;

    public CacheProxy(Subject target) {
        this.target = target;
    }

    // 프록시 객체가 최소 실행시(캐시 데이터가 없을 때) target을 실행하여 결과를 받아와 저장 후 반환함
    // 그 후 실행시 캐시가 있기 때문에 target을 실행하지 않고 캐시된 데이터를 반환함
    @Override
    public String operation() {
        log.info("프록시 호출!");
        if (cacheValue == null) {
            cacheValue = target.operation();
        }
        return cacheValue;
    }
}
