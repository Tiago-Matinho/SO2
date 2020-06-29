package so2.comunidade.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping(value="/")
    public static String home() {
        return "home";
    }

    @GetMapping(value = "/login")
    public static String login(){
        return "login";
    }

    @GetMapping(value = "/account")
    public static String account(){
        return "account";
    }
}
