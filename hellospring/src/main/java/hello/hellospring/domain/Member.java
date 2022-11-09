package hello.hellospring.domain;

// 본 예제에서는 데이터베이스에서 관리하는 대상이 회원이므로
// 회원이 domain이 됨

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // 이제 이 클래스는 JPA가 관리하는 entity가 된다.
public class Member {

    // Id는 PK이면서, insert할 때 자동으로 1씩 증가하며 생긴다. 이런 전략을 IDENTITY라고 한다.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id; // 식별자
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
