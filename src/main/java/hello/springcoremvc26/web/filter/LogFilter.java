package hello.springcoremvc26.web.filter;

import hello.springcoremvc26.web.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(
            FilterConfig filterConfig
    ) throws ServletException {
        log.info("LogFilter init()");
    }

    @Override
    public void destroy() {
        log.info("LogFilter destroy()");
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();

        String uuid = UUID.randomUUID().toString();
        req.setAttribute(SessionConst.LOG_ID, uuid);

        log.info("[{}][{}] LogFilter doFilter Start", requestURI, uuid);

        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("[{}][{}] Filter Error: [{}]", requestURI, uuid, e.toString());
            throw e;
        } finally {
            log.info("[{}][{}] LogFilter doFilter End", requestURI, uuid);
        }
    }
}
