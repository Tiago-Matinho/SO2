package so2.comunidade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import so2.comunidade.dados.Espaco;
import so2.comunidade.dados.Utilizador;
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
        return "/account";
    }

    @GetMapping("/findall")
    public String getAllEspaco(Model model){
        model.addAttribute("espacos", service.getAllEspaco());
        return "espaco/findall";
    }

    @GetMapping("/nome/")
    public void getEspacoByNome(Model model){
        Espaco espaco = new Espaco();
        model.addAttribute("Espaco", espaco);
        //return "espaco/nome";
    }

    @PostMapping("/nome/")
    public String postEspacoByNome(@ModelAttribute("Espaco") Espaco espaco){
        return "espaco/nome/" + espaco.getNome();
    }


    @GetMapping("/nome/{nome}")
    public String getEspacoByNome(@PathVariable("nome") String nome, Model model){
        model.addAttribute("espacos", service.getByNome(nome));
        return "espaco/nome";
    }

    @GetMapping("/novo")
    public String createEspacoGet(Model model){
        Espaco novo = new Espaco();
        model.addAttribute("Espaco", novo);
        return "espaco/novo";
    }

    @PostMapping("/novo")
    public String createEspaco(@ModelAttribute("Espaco") Espaco espaco){
        service.createEspaco(espaco.getNome(), espaco.getCoord());
        return "espaco/novo-sucessfully";//TODO fazer estas páginas (refresh da página para fazer um novo registo)
    }

}
