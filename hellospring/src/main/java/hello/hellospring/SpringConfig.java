package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 이 파일의 의미 : 여기있는 것들을 스프링 Bean으로 등록하라는 뜻
@Configuration
public class SpringConfig {
    private final MemberRepository memberRepository;

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    // 원래는 @Bean MemberRepository memberRepository(){} 함수로 빈에 등록한 다음 meberRepository를 받아야 하는데
    // SpringDataJpaMemberRepository는 JpaRepository를 상속함으로서 자동으로 빈에 올라갔으므로 별도의 등록 없이
    // 스프링 빈으로부터 AutoWired로 받아올 수 있다 (생성자 하나이므로 어노테이션은 생략)

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }



    /* Bean에 유일하게 등록된게 SpringDataJpaMemberRepository가 되도록 다른 @Bean은 주석 처리

    private final DataSource dataSource;
    private final EntityManager em;

    // @Configuration도 컴포넌트 스캔이므로
    // 스프링이 application.properties에 있는 DB정보에 따라 자동으로 DataSource를 만들어서 생성자에 넣어줌
    @Autowired
    public SpringConfig(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
        // Autowired와 마찬가지로, 그냥 memberRepository가 아니라 스프링빈에 등록된 memberRepository를 넣어줌
    }


    @Bean
    public MemberRepository memberRepository(){
        // return new MemberRepository();
        // return new JDBCMemberRepository(dataSource);
        // return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
        // 윗줄에서 아랫줄로 코드를 바꿨을 뿐인데 저장하는 공간이 DB로 바뀐다.
        // -> 자바의 다형성 덕분에 얻는 이득
    } 
    */
}
