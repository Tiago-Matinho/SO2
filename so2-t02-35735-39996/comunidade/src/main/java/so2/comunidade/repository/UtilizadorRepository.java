package so2.comunidade.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import so2.comunidade.dados.Utilizador;

@Repository
public interface UtilizadorRepository extends CrudRepository<Utilizador, Long> {

    Utilizador findUtilizadorById(long id);
}
