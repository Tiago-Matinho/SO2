package so2.comunidade.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import so2.comunidade.dados.Espaco;

import java.util.List;

@Repository
public interface EspacoRepository extends CrudRepository<Espaco, Long> {
    List<Espaco> findAll();
    Espaco findById(long id);
    List<Espaco> findByNome(String nome);
    List<Espaco> findByCoord(String coord);
    void deleteById(long id);
}
