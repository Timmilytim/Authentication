package com.Authentication.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Returns the login view
    }

    @GetMapping("/req/signup")
    public String signup() {
        return "signup"; // Returns the signup view
    }

}
