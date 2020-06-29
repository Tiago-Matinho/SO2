package so2.comunidade.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import so2.comunidade.dados.Authorities;

@Repository
public interface AuthoritiesRepository extends CrudRepository<Authorities, Long> {
    Authorities findByUsername(String username);
}
