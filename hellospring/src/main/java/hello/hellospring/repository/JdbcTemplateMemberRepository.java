package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// JDBC 템플릿을 사용하는 방법
public class JdbcTemplateMemberRepository implements MemberRepository{

    // 아래와 같은 형식을 권장한다.
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource) { 
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    // 복습) 스프링은 DB커넥션 정보를 바탕으로 DataSource 객체를 생성하고 스프링 빈으로 등록해둠
    // 스프링 빈에서 가져오는 것이므로 @AutoWired를 적어줘야 하지만, 생성자가 하나만 있을 때는 생략해도 됨

    @Override
    public Member save(Member member) {
        // insert 쿼리를 만드는 jdbcTemplate 문법 : SimpleJdbcInsert
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        // "name", member의 이름이 담긴 엔트리가 parameters라는 Map에 저장됨
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        // insert 쿼리 실행하고 key 반환
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        // DB에 저장된 key를 id로 설정
        member.setId(key.longValue());
        // 저장된 Member 객체 리턴
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // Jdbc 템플릿으로 쿼리를 날리고, 결과를 memberRowMapper()를 통해 매핑하여 리스트로 반환
        List<Member> result = jdbcTemplate.query("select * from member where id = ?",memberRowMapper(),id);
        // 리스트에 있는 값을 Optional로 바꾸어 return
        return result.stream().findAny();
        // JDBCMemberRepository의 findById와 비교하면 어마어마하게 줄어든 코드를 확인할 수 있다.
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?",memberRowMapper(),name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member",memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
