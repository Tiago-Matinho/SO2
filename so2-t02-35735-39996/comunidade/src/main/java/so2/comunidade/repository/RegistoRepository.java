package so2.comunidade.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import so2.comunidade.dados.Registo;

import java.util.Date;
import java.util.List;

@Repository
public interface RegistoRepository extends CrudRepository<Registo, Long> {
    List<Registo> findAll();
    List<Registo> getByEspacoAndDateAfter(long espaco, Date date);
    List<Registo> getByUtilizador(String utilizador);
    Registo findById(long id);
}
