package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 이 파일의 의미 : 여기있는 것들을 스프링 Bean으로 등록하라는 뜻
@Configuration
public class SpringConfig {
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
        // Autowired와 마찬가지로, 그냥 memberRepository가 아니라 스프링빈에 등록된 memberRepository를 넣어줌
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
