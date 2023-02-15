package hello.springcoremvc26.web;

import hello.springcoremvc26.domain.member.Member;
import hello.springcoremvc26.web.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
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
        // 로그인
        Member member = (Member) sessionManager.getSession(req).orElse(null);
        if (member == null) {
            return "home";
        }
        
        model.addAttribute("member", member);
        return "loginHome";
    }
}
