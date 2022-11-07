package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

// @Service
public class MemberService {

    private final MemberRepository memberRepository;

    // DI(Dependency Injection) : 직접 new로 생성하는게 아니라 외부에서 값을 받아서 저장하기만 함
    // @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 같은 이름의 사용자가 있는지 체크하는 함수
    private void validateDuplicateMember(Member member) {
        // ifPresent는 m의 값이 있으면 {}안의 내용을 실행한다.
        // 이처럼 null일 가능성이 있다면, Optional로 받아서 여러 메소드를 사용할 수 있다.
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        });
    } // 함수 추출 : Ctrl + Alt + Shift + T

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}

// cf. Ctrl + Alt + V로 먼저 함수를 호출하고, 자동으로 리턴값을 받게 할 수 있다.
