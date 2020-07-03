package so2.comunidade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import so2.comunidade.dados.Registo;
import so2.comunidade.dto.RegistoDto;
import so2.comunidade.services.RegistoService;

import javax.websocket.server.PathParam;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/account")
public class UtilizadorController {
    @Autowired
    private RegistoService registoService;

    //ver registo de utilizador
    @GetMapping("/registos")
    public String registoUtilizador(Map<String, Object> model) {
        List<Long> list = new LinkedList<>();
        List<Registo> registos = registoService.getByUtilizador();
        model.put("listaRemover", list);
        model.put("registos", registos);
        return "account/registos";
    }

    //apagar registos
    @DeleteMapping("/registos")
    public String deleteRegistoUtilizador(List<Long> list) {
        for (Long id : list) registoService.removeRegisto(id);
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
        if(registoService.createRegisto(registoDto))
            return "account";
        return "account/espaco-erro";
    }

    @DeleteMapping("/{id}")
    public String removeRegisto(@PathParam("id") long id) {
        registoService.removeRegisto(id);
        return "account/registos";
    }
}
