package hello.springcoremvc26.web.argumentresolver;

import hello.springcoremvc26.domain.member.Member;
import hello.springcoremvc26.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * `@Login Member`인지 확인
     */
    @Override
    public boolean supportsParameter(
            MethodParameter parameter
    ) {
        log.info("LoginMemberArgumentResolver supportsParameter");

        // 파라미터에 @Login 애노테이션이 있는가?
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        // 파라미터 type 이 Member type 인가?
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasMemberType;
    }

    /**
     * 컨트롤러 호출 직전에 호출되어서 필요한 파라미터 정보를 생성해준다.
     * 여기서는 세션에 있는 로그인 회원 정보인 `member`객체를 찾아서 반환해준다.
     * 이후, 스프링 MVC는 컨트롤러의 메서드를 호출하면서 여기에서 반환된 `member`객체를 파라미터에 전달해준다.
     */
    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        log.info("LoginMemberArgumentResolver resolveArgument");

        // 세션에서 맴버 가져오기
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);

        // 세션이 있으면, member 반환.
        // 세션이 없으면, null 반환.
        return session != null ? session.getAttribute(SessionConst.LOGIN_MEMBER) : null;
    }
}
