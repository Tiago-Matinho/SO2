package so2.comunidade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import so2.comunidade.dados.Utilizador;
import so2.comunidade.services.RegistoService;
import so2.comunidade.services.UtilizadorService;

import java.security.Principal;


@Controller
public class MainController {
    @Autowired
    private UtilizadorService utilizadorService;

    @Autowired
    private RegistoService registoService;

    @GetMapping(value="/")
    public String home(Model model) {
        model.addAttribute("ultimosRegistos", registoService.getRegistosUltimaHora());
        return "home";
    }

    @GetMapping(value = "/login")
    public String login(Principal principal){
        if(principal == null)
            return "login";
        else
            return "home";
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

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
