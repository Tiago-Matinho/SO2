package so2.comunidade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import so2.comunidade.dados.Utilizador;
import so2.comunidade.services.UtilizadorService;

import java.security.Principal;


@Controller
public class MainController {

    @Autowired
    private UtilizadorService utilizadorService;

    @GetMapping(value="/")
    public String welcome() {
        return "home";
    }

    @GetMapping(value = "/login")
    public String login(Principal principal){
        if(principal == null)
            return "login";
        else
            return "home";
    }

    @GetMapping(value = "/account")
    public String account(){
        return "account";
    }

    @GetMapping(value = "/join")
    public String joinGet(Model model) {
        Utilizador novo = new Utilizador();
        model.addAttribute("Utilizador", novo);
        return "join";
    }

    @PostMapping(value = "/join")
    public String joinPost(@ModelAttribute("Utilizador") Utilizador utilizador) {
        if(utilizadorService.createUtilizador(utilizador.getUsername(), utilizador.getPassword()))
            return "login";
        return "join";
    }
}
