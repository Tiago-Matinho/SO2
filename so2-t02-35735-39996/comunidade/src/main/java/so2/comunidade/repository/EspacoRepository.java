package so2.comunidade.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import so2.comunidade.dados.Espaco;

import java.util.List;

@Repository
public interface EspacoRepository extends CrudRepository<Espaco, Long> {
    List<Espaco> findAll();
    List<Espaco> findByNome(String nome);
    List<Espaco> findByCoord(String coord);
    void deleteByNome(String nome);
}
