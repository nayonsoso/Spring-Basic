package hello.hellospring.domain;

// 본 예제에서는 데이터베이스에서 관리하는 대상이 회원이므로
// 회원이 domain이 됨
public class Member {
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
