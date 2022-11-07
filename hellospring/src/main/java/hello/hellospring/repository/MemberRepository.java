package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;
// cf. public으로 선언된 Member 클래스를 임포트하는 이유
// public은 접근 제한에 관한 것이고, import는 같은 패키지가 아닐 때 들여오는 것
// 즉, public을 썼다고 해서 어디서든 쓸 수 있는게 아니라 *다른 패키지에 있으면 import 해야 한다*

// @Repository
public interface MemberRepository {
    Member save(Member member); // 쉽게 임포트하는 방법 : Alt + enter
    Optional<Member> findById(Long id); // Optional 설명 - null이 반환되는 경우에, 요즘은 Optional로 감싸서 반환하는걸 선호한다.
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
// 인터페이스 복습 :
// 인터페이스에서는 함수 앞에서 public abstract를 생략하더라도 모두 추상메소드이다
// default, static 표기한 것 제외