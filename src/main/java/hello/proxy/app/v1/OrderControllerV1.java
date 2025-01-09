package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.*;

//@RequestMapping // 스프링은 @Controller or @RequestMapping이 있으면 스프링 컨트롤러로 인식 -> 3.0 부터 @RestController 붙혀야함
@RestController
//@ResponseBody
public interface OrderControllerV1 {
    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/v1/no-log")
    String noLog();
}
