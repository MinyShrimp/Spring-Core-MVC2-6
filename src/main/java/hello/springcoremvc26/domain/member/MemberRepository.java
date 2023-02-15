package hello.springcoremvc26.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 동시성 문제가 고려되어 있지 않음.
 * 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
@Slf4j
@Repository
public class MemberRepository {
    private static final Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    /**
     * 저장
     *
     * @param member: 저장할 Member 객체
     * @return 저장된 Member
     */
    public Member save(Member member) {
        member.setId(++sequence);
        log.info("Member save: {}", member);
        store.put(member.getId(), member);
        return member;
    }

    /**
     * Member.loginId 가 아닌, Member.id 를 기반으로 찾음
     *
     * @param id: Member.id
     * @return 찾은 Member
     */
    public Member findById(Long id) {
        return store.get(id);
    }

    /**
     * Member.id 가 아닌, Member.loginId
     *
     * @param loginId: Member.loginId
     * @return 찾은 Member
     */
    public Optional<Member> findByLongId(String loginId) {
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }

    /**
     * 저장된 모든 Member List 를 새로 생성해서 반환
     *
     * @return 저장된 모든 Member List
     */
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    /**
     * 저장된 모든 Member 제거, 테스트 용도
     */
    public void clearStore() {
        store.clear();
    }
}
