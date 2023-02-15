package hello.springcoremvc26.web.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 세션 관리 매니저
 */
@Component
public class SessionManager {
    public static final String SESSION_COOKIE_NAME = "mySessionId";

    private final Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    /**
     * 세션 생성
     *
     * @param value 저장할 값
     * @param resp  HTTP Servlet Response
     */
    public void createSession(
            Object value,
            HttpServletResponse resp
    ) {
        // 세션 ID를 생성하고, 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        // 쿠키 생성
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        resp.addCookie(mySessionCookie);
    }

    /**
     * 세션 조회
     *
     * @param req HTTP Servlet Request
     * @return 조회된 세션에 대한 정보(값)
     */
    public Object getSession(
            HttpServletRequest req
    ) {
        return findCookie(req, SESSION_COOKIE_NAME)
                .map(value -> sessionStore.get(value.getValue()))
                .orElse(null);
    }

    /**
     * 세션 만료
     *
     * @param req HTTP Servlet Request
     */
    public void expire(
            HttpServletRequest req
    ) {
        findCookie(req, SESSION_COOKIE_NAME)
                .ifPresent(cookie -> sessionStore.remove(cookie.getValue()));
    }

    /**
     * 쿠키 찾기
     *
     * @param req        HTTP Servlet Request
     * @param cookieName 찾을 쿠키 이름
     * @return 찾은 쿠키
     */
    private Optional<Cookie> findCookie(
            HttpServletRequest req,
            String cookieName
    ) {
        if (req.getCookies() == null) {
            return Optional.empty();
        }

        return Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny();
    }
}
