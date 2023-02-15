package hello.springcoremvc26.web.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {
    @GetMapping("/session-info")
    public String sessionInfo(
            HttpServletRequest req
    ) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return "세션이 없습니다.";
        }

        /**
         * 모든 세션의 이름, 값 출력
         */
        session.getAttributeNames()
                .asIterator()
                .forEachRemaining(name -> log.info("session name = {}, value = {}", name, session.getAttribute(name)));

        // 세션 ID, JSESSIONID 값이다.
        log.info("sessionId = {}", session.getId());

        // 세션의 유효 시간 (second)
        log.info("getMaxInactiveInterval = {}", session.getMaxInactiveInterval());

        // 세션 생성 일시
        log.info("getCreationTime = {}", new Date(session.getCreationTime()));

        // 세션과 연결된 사용자가 최근에 서버에 접근한 시간
        // 클라이언트에서 서버로 sessionId를 요청한 경우에 갱신된다.
        log.info("getLastAccessedTime = {}", new Date(session.getLastAccessedTime()));

        // 새로 생성된 세션인지, 아니면 이미 과거에 만들어졌고, 클라이언트에서 서버로 sessionId를 요청해서 조회된 세션인지 여부
        log.info("isNew = {}", session.isNew());

        return "OK";
    }
}
