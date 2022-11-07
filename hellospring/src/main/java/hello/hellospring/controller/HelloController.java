package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    // MVC 방법 (Model - View - Controller)
    @GetMapping("hello") // 주소창에 hello라고 치면 아래 함수를 호출하도록 매핑
    public String hello(Model model){
        model.addAttribute("data", "hello!!!");
        return "hello";
    }

    // 외부에서 파라미터를 받는 방법 - get 방식(@RequestParam)
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model)
    {
        model.addAttribute("name", name);
        return "hello-template";
    }

    // API 방법 - String을 리턴 (이렇게는 잘 쓰이지 않음)
    @GetMapping("hello-string")
    @ResponseBody // HTTP 바디에 데이터를 담아 전달하겠다는 뜻
    public String helloString(@RequestParam("name") String name){
        return "hello" +name; // 뷰리졸버 거치지 않고, html 없이 그냥 스트링만 리턴됨
    }

    // API 방법 - 객체(json)를 리턴
    @GetMapping("hello-api")
    @ResponseBody
    public  Hello helloApir(@RequestParam("name") String name){
        Hello hello = new Hello(); // Ctrl + Shift + enter으로 세미콜론 빨리 찍을 수 있음
        hello.setName(name);
        return hello; //ResponseBody를 사용하고, 객체를 리턴하면 객체는 json으로 변환되어 넘어감
    }

    static class Hello{
        private String name;

        // getter and setter 단축키 : Ctrl + N > 모두 > getter and setter
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
