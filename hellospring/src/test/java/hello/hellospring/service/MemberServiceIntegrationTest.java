package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

// MemberServiceTest는 JVM 메모리선에서 모두 처리되었지만,
// 이 파일은 스프링과 DB를 이용한 테스트를 진행하는 '통합 테스트'파일임 >> @SpringBootTest 어노테이션 필요!
@SpringBootTest
@Transactional // test에 쓰는 Transactional 어노테이션은 각 테스트 후 DB데이터를 롤백(없던셈)하는 역할
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    // test 코드가 아니었으면 생성자를 통해서 DI 해주는게 좋지만, Test는 재사용과 관계 없고
    // 오로지 Test만을 위한 것이므로 위 방법처럼 간단하게 처리해도 된다.

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("spring100");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);

        // assertThrows로 테스트 하는 방법
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // 문법 설명 : ()->뒤에 적힌 메소드를 실행할 때 첫번째 파라미터에 있는 예외가 발생해야 한다.
        // 예외가 제대로 발생했다면, 해당하는 예외가 리턴된다. 이때 리턴된 예외를 통해서 메세지를 확인할 수 있다.
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");

    }
}