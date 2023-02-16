package hello.springcoremvc26.web.interceptor;

import hello.springcoremvc26.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = (String) request.getAttribute(SessionConst.LOG_ID);

        // @RequestMapping: HandlerMethod
        // 정적 리소스: ResourceHttpRequestMethod
        if (handler instanceof HandlerMethod) {
            // 호출할 컨트롤러 메서드의 모든 정보가 포함되어 있다.
            HandlerMethod hm = (HandlerMethod) handler;
        }

        log.info("[{}][{}] LogInterceptor preHandle", requestURI, uuid);
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = (String) request.getAttribute(SessionConst.LOG_ID);

        log.info("[{}][{}] LogInterceptor postHandle", requestURI, uuid);
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = (String) request.getAttribute(SessionConst.LOG_ID);

        log.info("[{}][{}] LogInterceptor afterComplete", requestURI, uuid);

        if (ex != null) {
            log.error("LogInterceptor afterComplete Error: ", ex);
            ex.printStackTrace();
        }
    }
}
