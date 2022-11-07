package hello.hellospring.repository;

// 자동 임포트 단축 키 : Alt + enter

import hello.hellospring.domain.Member;

import java.util.*;

// @Repository
public class MemoryMemberRepository implements MemberRepository {
    // 추상메서드 구현 단축키 : implements ~~ 뒤에서 Alt + enter
    private static Map<Long,Member> store = new HashMap<>(); // 저장하는 곳
    private static long sequence = 0L; // sequence는 Id를 생성하는 목적, static이므로 모든 객체가 공유
    // 0L 이란, long 타입의 0을 의미한다.

    @Override
    public Member save(Member member) { // ID에 1 더해서 ID 설정하는 함수
        member.setId(++sequence);
        store.put(member.getId(),member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        // 잘못된 아이디가 들어오면 Member를 찾지 못해 null이 반환될 수 있으므로 Optional.ofNullable 안에 넣어줌
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name)).findAny();
        // stream.filter를 이용해 store안에 있는 values중 name과 같은 것들만 골라낸다.
        // findAny를 이용해 하나라도 발견하면 리턴한다.
        // 하나도 없으면 Optional에 null이 포함되어 반환된다.
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
        // 저장된 모든 values(Member)를 ArrayList 형태로 반환
        // 자바 실무에서는 주로 ArrayList를 사용한다고 함
    }

    public void clearStore() {
        store.clear();
    }
}
