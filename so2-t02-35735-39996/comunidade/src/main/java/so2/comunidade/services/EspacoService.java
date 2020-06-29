package so2.comunidade.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import so2.comunidade.dados.Espaco;
import so2.comunidade.repository.EspacoRepository;

import java.util.List;

@Service
public class EspacoService {

    @Autowired
    private EspacoRepository repository;

    public List<Espaco> getAllEspaco() {
        return this.repository.findAll();
    }

    public Espaco getById(long id) {
        return this.repository.findById(id);
    }

    public List<Espaco> getByNome(String nome) {
        return this.repository.findByNome(nome);
    }

    public List<Espaco> getByCoord(String coord) {
        return this.repository.findByCoord(coord);
    }

    public void removeEspaco(long id) {
        this.repository.deleteById(id);
    }

    public String updateEspaco(Espaco espaco) {
        Espaco update = new Espaco();
        update.setId(espaco.getId());
        update.setNome(espaco.getNome());
        update.setCoord(espaco.getCoord());
        this.repository.deleteById(espaco.getId());
        this.repository.save(update);
        return "Alterado com sucesso: " + update.getId();
    }

    public String createEspaco(String nome, String coord) {
        Espaco novo = new Espaco(nome, coord);
        this.repository.save(novo);
        return "Adicionado com sucesso: " + novo.getId();
    }

}
