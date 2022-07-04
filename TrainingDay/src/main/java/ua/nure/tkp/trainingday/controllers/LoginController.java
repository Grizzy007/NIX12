package ua.nure.tkp.trainingday.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.nure.tkp.trainingday.entity.User;
import ua.nure.tkp.trainingday.entity.dto.UserDto;
import ua.nure.tkp.trainingday.repository.UserRepo;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    UserRepo userRepo;

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping(value = "/success")
    public String getSuccessPage() {
        return "redirect:/home";
    }

    @GetMapping(value = "/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(UserDto user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        User entity = new User(user.getLogin(), passwordEncoder.encode(user.getPassword()), user.getAge(), user.getNickname());
        userRepo.save(entity);
        return "redirect:/home";
    }
}
