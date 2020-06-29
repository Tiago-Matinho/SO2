package so2.comunidade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import so2.comunidade.dados.Registo;
import so2.comunidade.services.RegistoService;

import java.util.List;

@RestController
public class RegistoController {
    @Autowired
    RegistoService service;

    @GetMapping("/registo/findall")
    public List<Registo> findAll(){
        return service.getAllRegisto();
    }

    @GetMapping("/registo/ei={espacoId}")
    public List<Registo> findByEspaco_id(@PathVariable("espacoId") long espacoId){
        return service.findByEspacoAndDateAfter(espacoId);
    }

    @GetMapping("/registo/id={id}")
    public Registo findById(@PathVariable("id") long id){
        return service.findById(id);
    }

}
