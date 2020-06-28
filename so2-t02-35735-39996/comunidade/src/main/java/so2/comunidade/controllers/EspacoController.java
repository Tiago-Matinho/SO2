package so2.comunidade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import so2.comunidade.dados.Espaco;
import so2.comunidade.services.EspacoService;

import java.util.List;

@RestController
@RequestMapping("/espaco")
public class EspacoController {

    @Autowired
    private EspacoService espacoService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Espaco> getAllEspaco(){
        return espacoService.getAllEspaco();
    }

    @RequestMapping(value = "/id={id}", method = RequestMethod.GET)
    public Espaco getEspacoById(@PathVariable("id") long id) {
        return espacoService.getById(id);
    }

    @RequestMapping(value = "/id={id}", method = RequestMethod.DELETE)
    public String deleteEspacoById(@PathVariable("id") long id) {
        return espacoService.removeEspaco(id);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateEspaco(@RequestBody Espaco espaco) {
        return espacoService.updateEspaco(espaco);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createEspaco(@RequestBody Espaco espaco) {
        return espacoService.createEspaco(espaco);
    }

    @RequestMapping(value = "/nome={nome}", method = RequestMethod.GET)
    public List<Espaco> findEspacoByNome(@PathVariable("nome") String nome) {
        return espacoService.getByNome(nome);
    }

    @RequestMapping(value = "/coordenadas={coord}", method = RequestMethod.GET)
    public List<Espaco> findEspacoByCoord(@PathVariable("coord") String coord) {
        return espacoService.getByCoord(coord);
    }

}
