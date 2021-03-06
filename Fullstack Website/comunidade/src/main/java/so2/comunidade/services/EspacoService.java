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

    public Espaco getByNome(String nome) {
        return this.repository.findByNome(nome);
    }

    public List<Espaco> getEspacoContaining(String nome) {
        return this.repository.findByNomeContaining(nome);
    }

    public void createEspaco(String nome, String coord) {
        Espaco novo = new Espaco(nome, coord);
        this.repository.save(novo);
    }
}
