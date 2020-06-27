package so2.comunidade.repository;

import org.springframework.data.repository.CrudRepository;
import so2.comunidade.dados.Registo;

import java.util.Date;
import java.util.List;

public interface RegistoRepository extends CrudRepository<Registo, Long> {

    List<Registo> findByEspacoAndDateAfter(long espaco, Date date);
    List<Registo> findById(long id);
    List<Registo> findAll();
}
