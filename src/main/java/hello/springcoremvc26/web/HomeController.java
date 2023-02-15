package hello.springcoremvc26.web;

import hello.springcoremvc26.domain.member.Member;
import hello.springcoremvc26.web.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final SessionManager sessionManager;

    @GetMapping("/")
    public String homeLogin(
            HttpServletRequest req,
            Model model
    ) {
        // 세션이 없으면 home
        HttpSession session = req.getSession(false);
        if (session == null) {
            return "home";
        }

        // 로그인
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
