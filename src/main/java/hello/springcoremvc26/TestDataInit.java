package hello.springcoremvc26;

import hello.springcoremvc26.domain.item.Item;
import hello.springcoremvc26.domain.item.ItemRepository;
import hello.springcoremvc26.domain.member.Member;
import hello.springcoremvc26.domain.member.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        // 아이템
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));

        // 맴버
        memberRepository.save(new Member("test", "테스터", "test!"));
    }
}
