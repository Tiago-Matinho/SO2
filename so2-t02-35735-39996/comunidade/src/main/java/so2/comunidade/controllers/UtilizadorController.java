package so2.comunidade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import so2.comunidade.dados.Espaco;
import so2.comunidade.dto.RegistoDto;
import so2.comunidade.services.EspacoService;
import so2.comunidade.services.RegistoService;

import java.util.List;
import java.util.Map;


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
        List<RegistoDto> registos = registoService.getByUtilizador();
        model.addAttribute("registos", registos);
        return "account/registos";
    }

    //apagar registos
    @RequestMapping(value = "/registos", method = RequestMethod.POST)
    public String deleteRegistoUtilizador(@RequestParam(value = "id") long id , Model model) {
        registoService.removeRegisto(id);
        return registoUtilizador(model);
    }

    //registo novo
    @GetMapping("/registo-novo")
    public String getRegistoNovo(Map<String, Object> model) {
        RegistoDto registoDto = new RegistoDto();
        List<Espaco> espacos = espacoService.getAllEspaco();
        model.put("registoNovo", registoDto);
        model.put("espacosExistentes", espacos);
        return "account/registo-novo";
    }

    @PostMapping(value = "/registo-novo")
    public String postRegistoNovo(@ModelAttribute("registoNovo") RegistoDto registoDto, Model model) {
        if(registoService.createRegisto(registoDto))
            return registoUtilizador(model);
        return "account/espaco-erro";
    }
}
