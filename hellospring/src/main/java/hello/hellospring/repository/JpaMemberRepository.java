package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em; // JPA는 EntityManager로 모든 걸 동작시킨다.

    // application.prperties 파일에서 jpa를 임포트하면
    // 스프링 부트에서 EntityManager를 생성해서 자동으로 DB와 연결까지 시켜준다.
    // 우리는 만들어진 EntiytManager를 받아오기만 하면 된다.
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
        // 이렇게 하면 JPA가 쿼리도 만들고, setId도 하여 DB에 저장시켜준다.
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id); // pram#1:조회할 타입, pram#2: 식별자 PK
        return Optional.ofNullable(member); // 조회한 멤버를 Optional로 포장해서 반환
    }

    // PK인 id를 이용하는게 아닌 findByName이나 findAll 등은 JPQL을 작성해줘야 한다.
    @Override
    public Optional<Member> findByName(String name) {
        // JPQL 문법
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // JPQL : 객체를 대상으로 쿼리를 날림
        // 다른 SQL과 차이점 : 보통 select * 혹은 id, name로 받아오고, 매핑을 해줘야 하지만 JPSQL은 객체 자체를 가져옴
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
