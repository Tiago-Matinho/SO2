package so2.comunidade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import so2.comunidade.services.RegistoService;

import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/registo")
public class RegistoController {
    @Autowired
    private RegistoService service;

    @GetMapping("/bulkcreate")
    public String bulkcreateEspaco() {
        Calendar cal = Calendar.getInstance();
        Date data = cal.getTime();

        service.createRegisto(data, "Pingo Doce Graça", 1);
        service.createRegisto(data, "Pingo Doce Graça", 2);
        service.createRegisto(data, "Pingo Doce Évora", 1);
        service.createRegisto(data, "Lidl Évora", 1);
        return "/account/registos"; //FIXME
    }

    //Bloquear para admin
    @GetMapping("findall")
    public String findAll(Model model){
        model.addAttribute("registos", service.getAllRegisto());
        return "registo/findall";
    }

    @GetMapping("/espaco/{nome}")
    public String findByNomeEspaco(@PathVariable("nome") String nome, Model model){
        model.addAttribute("registos", service.getByNome_espacoAndDateAfter(nome));
        return "registo/findall";
    }
}
