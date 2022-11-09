package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// 컨트롤러라는 어노테이션을 하면, 스프링 컨테이너에서 MemberController라는 객체를 만들어서 관리를 함
@Controller
public class MemberController {
    // 스프링 컨테이너에서 등록된 것을 가져다 쓰는데에는 여러가지 장점이 있다. (추후 설명)
    // 무튼 그런 이유로 스프링 컨테이너에 등록된 memberService를 찾아서 자동으로 연결해주는 Autowired를 사용할 수있다.
    // 주의 : 서비스를 스프링 컨테이너에 등록하기 위해서는 @Service라는 어노테이션을 사용해야 한다. Repository도 마찬가지!
    private final MemberService memberService;

    // Autowired에 의해 스프링 컨테이너에 등록된 memberService가 생성자 매개변수로 들어간다.
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/members/new") // URL로 값을 입력했을 때 호출됨
    public String createForm(){
        return "members/createMemberForm"; // template의 members/createMemberForm.html로 이동
    }

    // html의 form테그에서 action="/members/new" method="post"로 입력했기 떄문에
    // 사용자가 form에 입력을 완료하면 아래 함수가 호출됨
    // 이때 매개변수에 form의 input에서 작성한 것이 전달됨
    // 내부적으로 MemberForm form에 setName해서 이름을 설정한 다음 매개변수로 넘겨줌
    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList"; // templat에 있는 html을 찾아서 model 전달
    }
}