package hello.springcoremvc26.web.interceptor;

import hello.springcoremvc26.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = (String) request.getAttribute(LogInterceptor.LOG_ID);
        log.info("[{}][{}] LoginCheckInterceptor preHandle", requestURI, uuid);

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청 {}", requestURI);
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false;
        }

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
        String uuid = (String) request.getAttribute(LogInterceptor.LOG_ID);

        log.info("[{}][{}] LoginCheckInterceptor postHandle", requestURI, uuid);
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = (String) request.getAttribute(LogInterceptor.LOG_ID);

        log.info("[{}][{}] LoginCheckInterceptor afterCompletion", requestURI, uuid);
    }
}
