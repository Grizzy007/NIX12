package ua.nure.tkp.trainingday.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @GetMapping(value = "/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping(value = "/success")
    public String getSuccessPage(){
        return "success";
    }
}
