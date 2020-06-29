package so2.comunidade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import so2.comunidade.dados.Espaco;
import so2.comunidade.services.EspacoService;

import java.util.List;

@Controller
@RequestMapping("/espaco")
public class EspacoController {
    @Autowired
    private EspacoService espacoService;

    @GetMapping("/bulkcreate")
    public String bulkcreateEspaco() {
        Espaco espaco = new Espaco("Pingo Doce", "12334");
        espacoService.createEspaco(espaco);
        espaco = new Espaco("Pingo Doce", "rgsdg34");
        espacoService.createEspaco(espaco);
        espaco = new Espaco("Lidl", "12334");
        espacoService.createEspaco(espaco);
        espaco = new Espaco("asda", "123asdas34");
        espacoService.createEspaco(espaco);
        return "account";
    }

    @GetMapping("/findall")
    public String getAllEspaco(Model model){
        model.addAttribute(espacoService.getAllEspaco());
        return "espaco/findall";
    }

    @GetMapping("/id={id}")
    public String getEspacoById(@PathVariable("id") long id, Model model) {
        model.addAttribute(espacoService.getById(id));
        return "espaco/id";
    }

}
