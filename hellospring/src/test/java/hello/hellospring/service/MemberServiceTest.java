package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach // 각 테스트 전에 실행됨 - 테스트마다 새로운 객체를 만드는 이유 : 테스트가 동일한 조건에서 실행되는 것을 보장하기 위해
    public void beforeEach(){
        // Dependency Injection : MemberService 입장에서, 외부에서 값을 받아서 생성자가 완성되도록 하는 것.
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach // 각 테스트 후에 실행됨
    public void afterEach() {
        memberRepository.clearStore();
    } 
    // 클리어해줘야 하는 이유 : 
    // 서비스는 memberRepository라는 멤버를 저장소로 사용하는데, memberRepository는 store을 static 멤버로 사용하므로,
    // 어떤 멤버 서비스로 join을 하던 하나의 store을 공유하게 된다. 따라서 테스트 후 남아있는 데이터를 삭제 시켜줄 필요가 있다.

    // cf. 테스트 코드는 한글로 적기도 한다.
    @Test
    void 회원가입() {
        // given - hello로 설정된 멤버가 있음
        Member member = new Member();
        member.setName("hello");

        // when - memberService에 멤버를 join시킴
        Long saveId = memberService.join(member);

        // then - 저장한 아이디와 같은 아이디를 갖는 멤버를 찾은 다음, 이름을 비교함
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
        // 대부분의 테스트 코드는 위 세가지로 나누어 정의할 수 있고, 이렇게 분류해두면 테스트 코드에 대한 이해도도 높아지므로
        // given-when-then 문법을 사용하실 추천한다.
    }

    @Test
    public void 중복_회원_예외(){
        // given : 같은 이름이 주어진 상황
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when : 같은 이름의 멤버를 등록하려 할 때
        memberService.join(member1);
        
        /* try-catch로 예외 테스트 하는 방법
        try{
            memberService.join(member2);
            fail(); // 예외가 발생하여 catch로 점프하지 않으면 테스트 실패
        } catch(IllegalStateException e){
            // catch문으로 점프했으면 우리가 의도했던 메세지가 잘 출력되는지 확인
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
        }
        */
        
        // assertThrows로 테스트 하는 방법
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // 문법 설명 : ()->뒤에 적힌 메소드를 실행할 때 첫번째 파라미터에 있는 예외가 발생해야 한다.
        // 예외가 제대로 발생했다면, 해당하는 예외가 리턴된다. 이때 리턴된 예외를 통해서 메세지를 확인할 수 있다.
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");



    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}