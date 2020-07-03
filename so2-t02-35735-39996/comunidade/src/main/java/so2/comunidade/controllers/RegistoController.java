package so2.comunidade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import so2.comunidade.services.RegistoService;

@Controller
@RequestMapping("/registo")
public class RegistoController {
    @Autowired
    private RegistoService service;

    @GetMapping("/bulkcreate")
    public String bulkcreateEspaco() {
        service.createRegisto(1, 1);
        service.createRegisto(1, 2);
        service.createRegisto(1, 1);
        service.createRegisto(2, 1);
        service.createRegisto(1, 4);
        return "/account/registos";
    }

    @GetMapping("findall")
    public String findAll(Model model){
        model.addAttribute("registos", service.getAllRegisto());
        return "registo/findall";
    }

    @GetMapping("/espaco={id}")
    public String findByEspaco_id(@PathVariable("id") long id, Model model){
        model.addAttribute("registos", service.getByEspacoAndDateAfter(id));
        return "registo/findall";
    }

    @GetMapping("/id={id}")
    public String findById(@PathVariable("id") long id, Model model){
        model.addAttribute("registos", service.findById(id));
        return "registo/findall";
    }

}
