package hello.springcoremvc26.web.login;

import hello.springcoremvc26.domain.login.LoginService;
import hello.springcoremvc26.domain.member.Member;
import hello.springcoremvc26.dto.login.LoginDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(
            @ModelAttribute("loginForm") LoginDto form
    ) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(
            @Validated @ModelAttribute("loginForm") LoginDto form,
            BindingResult bindingResult,
            HttpServletResponse resp
    ) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        // 로그인 시도
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}", loginMember);

        // 로그인 실패 시
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리 - 쿠키 생성
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        resp.addCookie(idCookie);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(
            HttpServletResponse resp
    ) {
        expireCookie(resp, "memberId");
        return "redirect:/";
    }

    private void expireCookie(
            HttpServletResponse resp,
            String cookieName
    ) {
        // 쿠키의 만료 시간을 0으로 만든다.
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }
}
