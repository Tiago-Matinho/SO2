package so2.comunidade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import so2.comunidade.services.RegistoService;

import java.util.Map;

@Controller
@RequestMapping("/registo")
public class RegistoController {
    @Autowired
    private RegistoService service;

    @RequestMapping(value = "/pesquisa-nome")
    public String findByNomeEspaco(@RequestParam(value = "nome") String nome, Map<String, Object> model) {
        return findByNome(nome, model);
    }

    public String findByNome(String nome, Map<String, Object> model) {
        model.put("espaco", nome);
        model.put("registos", service.pesquisaNome(nome));
        return "registo/pesquisa-nome";
    }

    @GetMapping("/nome/{nome}")
    public String getEspacoByNome(@PathVariable("nome") String nome, Model model) {
        model.addAttribute("niveis", service.getByespacoAndDateAfter(nome));
        return "registo/nome";
    }
}
