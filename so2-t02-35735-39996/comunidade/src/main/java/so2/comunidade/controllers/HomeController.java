package so2.comunidade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import so2.comunidade.dados.Utilizador;
import so2.comunidade.services.UtilizadorService;


@Controller
public class HomeController {

    @Autowired
    private UtilizadorService utilizadorService;

    @GetMapping(value="/")
    public String welcome() {
        return "home";
    }

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

    @GetMapping(value = "/account")
    public String account(){
        return "account";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute("Utilizador") Utilizador utilizador) {
        if(utilizadorService.createUtilizador(utilizador.getUsername(), utilizador.getPassword()))

        return "join";
    }
}
