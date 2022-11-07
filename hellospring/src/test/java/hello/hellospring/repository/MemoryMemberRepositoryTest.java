package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 테스트끼리 영향받지 않게 단일 테스트가 끝날 때마다 repository를 clear해주는 코드
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test // Test 어노테이션을 쓰면 심지어 이 함수만 실행하는 것도 가능하다 - 마치 main 메소드를 실행하듯이!
    public void save(){
        Member member = new Member();
        member.setName("spring");
        repository.save(member); // ID에 1 더해서 저장하는 메소드

        // findId를 통해서 member의 id가 잘 저장되었는지 확인하려 한다!
        Member result = repository.findById(member.getId()).get();
        // get()을 하는 이유 : 원래 Optional<Member>가 리턴타입인데, 바로 Member로 받기 위해서

        // 이렇게 if를 통해 얻은 객체가 원본 객체와 동일한지 (메모리상에서 같다는 것은 ==) 확인하려 한다!
        // System.out.println("result : " + (result==member));
        // 하지만 매번 이렇게 콘솔에 하나하나 출력해볼 수는 없으니..

        // assert라는 기능을 이용하면 된다!!

        // assertXXX메소드의 매개변수는 expected, actual 순으로 되어야 한다. (Ctrl + p로 파라미터 확인 가능)
        // 여기에서는 result가 member로 예상되는 것이므로 expected = member / actual = result
        // assertEquals(member, result);
        // 비록 눈에 보이게 출력되는 건 없으나, 창 가장 아래 "테스트 성공"을 통해 통과했음을 알 수 있다!

        // 요즘에 많이 쓰이는 것 assertThat
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        // get함수로 Optional을 까고 Member를 리턴받을 수 있다.
        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findByAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        // 같은 내용에 이름만 바꾸고 싶을 때 : 복사한 다음 Shift + F6
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // get함수로 Optional을 까고 Member를 리턴받을 수 있다.
        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }

}
