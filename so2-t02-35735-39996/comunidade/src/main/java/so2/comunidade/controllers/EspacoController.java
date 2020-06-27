package so2.comunidade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import so2.comunidade.repository.EspacoRepository;
import so2.comunidade.dados.Espaco;

import java.util.List;

@RestController
public class EspacoController {
    @Autowired
    EspacoRepository repository;

    @GetMapping("/espaco/bulkcreate")
    public String bulkcreate() {

        repository.save(new Espaco("Pingo Doce", "Évora"));
        repository.save(new Espaco("Continente", "Évora"));
        repository.save(new Espaco("Pingo Doce", "Lisboa"));
        repository.save(new Espaco("Lidl", "Lisboa"));
        return "Espacos criados";
    }

    @GetMapping("/espaco/findall")
    public List<Espaco> findAll(){
        return repository.findAll();
    }

    @GetMapping("/espaco/id={espacoId}")
    public Espaco findEspaco(@PathVariable("espacoId") int espacoId) {
        return repository.findById(espacoId);
    }

    @GetMapping("/espaco/nome={espacoNome}")
    public List<Espaco> findEspaco(@PathVariable("espacoNome") String espacoNome) {
        return repository.findByNome(espacoNome);
    }

}
