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

    //registo novo (usando um espaço ja registado)
    @GetMapping("/registo-novo")
    public String getRegistoNovo(Model model) {
        Registo registo = new Registo();
        model.addAttribute("Registo", registo);
        return "account/registo-novo";
    }

    @PostMapping("/registo-novo")
    public String postRegistoNovo(@ModelAttribute("Registo") Registo registo) {
        if(!espacoService.valida(registo.getEspaco()))
            return "/account/espacoNotFound";
        registoService.createRegisto(registo.getEspaco(), registo.getNivel());
        return "account/registos";
    }

    //registo novo (usando um novo espaço)
    @GetMapping("/registo-novo-espaco")
    public String getRegistoNovoEspaco(Model model) {
        RegistoDto registoDto = new RegistoDto();
        model.addAttribute("RegistoDto", registoDto);
        return "account/registo-novo-espaco";
    }

    // Adicionar novo tipo de objecto? que tenha o espaço e o registo?
    @PostMapping("/registo-novo-espaco")
    public String postRegistoNovoEspaco(@ModelAttribute("RegistoDto") RegistoDto registoDto) {
        Espaco espaco = new Espaco(registoDto.getNome(), registoDto.getCoord());

        long id = espacoService.createEspaco(espaco.getNome(), espaco.getCoord());

        //todo verificar se foi possivel

        registoService.createRegisto(id, registoDto.getNivel());

        return "account/registos";
    }

    @DeleteMapping("/{id}")
    public String removeRegisto(@PathParam("id") long id) {
        registoService.removeRegisto(id);
        return "account/registos";
    }
}
