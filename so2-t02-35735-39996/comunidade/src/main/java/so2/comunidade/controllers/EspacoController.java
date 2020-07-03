package so2.comunidade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import so2.comunidade.services.EspacoService;


@Controller
@RequestMapping("/espaco")
public class EspacoController {
    @Autowired
    private EspacoService service;

    @GetMapping("/bulkcreate")
    public String bulkcreateEspaco() {
        service.createEspaco("Pingo Doce", "12334");
        service.createEspaco("Pingo Doce", "rgsdg34");
        service.createEspaco("Lidl", "12334");
        service.createEspaco("asda", "123asdas34");
        return "/account/registos";
    }

    @GetMapping("/findall")
    public String getAllEspaco(Model model){
        model.addAttribute("espacos", service.getAllEspaco());
        return "espaco/findall";
    }

    @PostMapping("/nome")
    public String postEspacoByNome(
            @RequestParam(value = "nome") String nome){
        return "espaco/nome/" + nome;
    }

    @GetMapping("/nome/{nome}")
    public String getEspacoByNome(@PathVariable("nome") String nome, Model model){
        model.addAttribute("espacos", service.getByNome(nome));
        return "espaco/nome";
    }


}
