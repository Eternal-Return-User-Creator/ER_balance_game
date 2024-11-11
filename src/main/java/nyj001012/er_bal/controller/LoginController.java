package nyj001012.er_bal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class LoginController {

    @GetMapping("login")
    @ResponseBody
    public String login() {
        return "Login";
    }
}
