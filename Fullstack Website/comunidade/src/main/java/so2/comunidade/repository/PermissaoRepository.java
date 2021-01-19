package so2.comunidade.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import so2.comunidade.dados.Permissao;

@Repository
public interface PermissaoRepository extends CrudRepository<Permissao, String> {

}
