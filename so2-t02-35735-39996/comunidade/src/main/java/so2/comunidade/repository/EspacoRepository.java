package so2.comunidade.repository;


import org.springframework.data.repository.CrudRepository;
import so2.comunidade.dados.Espaco;

import java.util.List;

public interface EspacoRepository extends CrudRepository<Espaco, Long> {
    List<Espaco> findByNome(String nome);
    List<Espaco> findByCoord(String coord);
    List<Espaco> findAll();
    Espaco findById(long id);
}
