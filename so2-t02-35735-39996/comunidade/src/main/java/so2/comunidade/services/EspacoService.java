package so2.comunidade.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import so2.comunidade.dados.Espaco;
import so2.comunidade.repository.EspacoRepository;

import java.util.List;
import java.util.Optional;

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

    public long createEspaco(String nome, String coord) {
        Espaco novo = new Espaco(nome, coord);
        this.repository.save(novo);
        return novo.getId();
    }

    public boolean valida(long espaco_id) {
        return repository.findById(espaco_id) != null;
    }
}
