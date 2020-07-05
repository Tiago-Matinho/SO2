package so2.comunidade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import so2.comunidade.dados.Espaco;
import so2.comunidade.dto.RegistoDto;
import so2.comunidade.services.EspacoService;
import so2.comunidade.services.RegistoService;

import javax.websocket.server.PathParam;
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
    public String deleteRegistoUtilizador(@RequestParam long id, Model model) {
        registoService.removeRegisto(id);
        List<RegistoDto> registos = registoService.getByUtilizador();
        model.addAttribute("registos", registos);
        return "account/registos";
    }

    //registo novo
    @GetMapping("/registo-novo")
    public String getRegistoNovo(Map<String, Object> model) {
        RegistoDto registoDto = new RegistoDto();
        List<Espaco> espacos = espacoService.getAllEspaco();
        model.put("RegistoDto", registoDto);
        model.put("espacos", espacos);
        return "account/registo-novo";
    }

    @RequestMapping(value = "/registos-novo", method = RequestMethod.POST)
    public String postRegistoNovo(@RequestParam RegistoDto registoDto) {
        if(registoService.createRegisto(registoDto))
            return "account/registos";
        return "account/espaco-erro";
    }

    @DeleteMapping("/{id}")
    public String removeRegisto(@PathParam("id") long id) {
        registoService.removeRegisto(id);
        return "account/registos";
    }
}
