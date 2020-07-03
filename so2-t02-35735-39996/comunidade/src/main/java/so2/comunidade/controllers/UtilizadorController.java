package so2.comunidade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import so2.comunidade.dados.Espaco;
import so2.comunidade.dados.Registo;
import so2.comunidade.dto.RegistoDto;
import so2.comunidade.services.EspacoService;
import so2.comunidade.services.RegistoService;

import javax.websocket.server.PathParam;


@Controller
@RequestMapping("/account")
public class UtilizadorController {
    @Autowired
    private RegistoService registoService;
    @Autowired
    private EspacoService espacoService;

    //ver registo de utilizador
    @GetMapping("/registos")
    public String registoUtilizador(Model model) {
        model.addAttribute("registos", registoService.getByUtilizador());
        return "account/registos";
    }

    //registo novo
    @GetMapping("/registo-novo")
    public String getRegistoNovo(Model model) {
        RegistoDto registoDto = new RegistoDto();
        model.addAttribute("RegistoDto", registoDto);
        return "account/registo-novo";
    }

    @PostMapping("/registo-novo")
    public String postRegistoNovo(@ModelAttribute("RegistoDto") RegistoDto registoDto) {
        //TODO
        return "account/registos";
    }

    @DeleteMapping("/{id}")
    public String removeRegisto(@PathParam("id") long id) {
        registoService.removeRegisto(id);
        return "account/registos";
    }
}
