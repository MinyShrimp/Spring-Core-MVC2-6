package hello.springcoremvc26.web.filter;

import hello.springcoremvc26.web.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {
    private static final String[] whitelist = {"/", "/members/add", "/login", "/logout", "/css/*"};

    @Override
    public void init(
            FilterConfig filterConfig
    ) throws ServletException {
        log.info("LoginCheckFilter init()");
    }

    @Override
    public void destroy() {
        log.info("LoginCheckFilter destroy()");
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String requestURI = req.getRequestURI();
        String uuid = (String) req.getAttribute(SessionConst.LOG_ID);

        try {
            log.info("[{}][{}] LoginFilter doFilter Start", requestURI, uuid);

            if (isLoginCheckPath(requestURI)) {
                log.info("[{}][{}] 인증 체크 로직 실행", requestURI, uuid);

                HttpSession session = req.getSession(false);
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("[{}][{}] 미인증 사용자 요청", requestURI, uuid);
                    resp.sendRedirect("/login?redirectURL=" + requestURI);
                    return;
                }
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            // 예외 로깅 가능하지만, 톰캣까지 예외를 보내주어야함.
            log.error(e.toString());
            throw e;
        } finally {
            log.info("[{}][{}] LoginFilter doFilter End", requestURI, uuid);
        }
    }

    /**
     * 화이트 리스트 검사
     */
    private boolean isLoginCheckPath(
            String requestURI
    ) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
