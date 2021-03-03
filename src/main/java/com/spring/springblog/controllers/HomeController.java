package com.spring.springblog.controllers;

import com.spring.springblog.models.User;
import com.spring.springblog.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final UserRepository userDao;
    private final PasswordEncoder encoder;

    public HomeController(UserRepository userDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("title", "Welcome to Blogging Bee");
        return "welcome";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Home");
        return "home";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("title", "Login");
        return "login";
    }

    @GetMapping("/sign-up")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "Sign Up");
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String createUser(@ModelAttribute User user) {
        String password = user.getPassword();
        String hash = encoder.encode(password);
        user.setPassword(hash);
        userDao.save(user);

        return "redirect:/login";
    }
}
