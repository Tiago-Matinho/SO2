package so2.comunidade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
        return "user/account";
    }

    @GetMapping("/findall")
    public String getAllEspaco(Model model){
        model.addAttribute(service.getAllEspaco());
        return "espaco/findall";
    }

    @GetMapping("/id={id}")
    public String getEspacoById(@PathVariable("id") long id, Model model) {
        model.addAttribute(service.getById(id));
        return "espaco/id";
    }

}
