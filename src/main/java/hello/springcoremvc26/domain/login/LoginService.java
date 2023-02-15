package hello.springcoremvc26.domain.login;

import hello.springcoremvc26.domain.member.Member;
import hello.springcoremvc26.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    /**
     * 로그인 비즈니스 로직
     *
     * @param loginId:  로그인 ID
     * @param password: 로그인 비밀번호
     * @return 성공: 로그인된 Member, 실패: null
     */
    public Member login(String loginId, String password) {
        log.info("LoginService: '{}', '{}'", loginId, password);
        return memberRepository.findByLongId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
