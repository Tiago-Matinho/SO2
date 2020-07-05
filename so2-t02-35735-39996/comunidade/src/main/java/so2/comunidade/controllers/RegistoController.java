package so2.comunidade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import so2.comunidade.dados.Espaco;
import so2.comunidade.dto.EspacoDto;
import so2.comunidade.dto.RegistoDto;
import so2.comunidade.services.RegistoService;

import javax.websocket.server.PathParam;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/registo")
public class RegistoController {
    @Autowired
    private RegistoService service;

    //TODO: Bloquear para admin
    @GetMapping("findall")
    public String findAll(Model model){
        model.addAttribute("registos", service.getAllRegisto());
        return "registo/findall";
    }

    @RequestMapping(value = "/nome")
    public String findByNomeEspaco(@RequestParam(value = "nome") String nome, Model model) {
        System.out.println("Nome: "+nome);
        //model.addAttribute("registos", service.getByespacoAndDateAfter(nome));
        return "registo/findall";
    }
}
