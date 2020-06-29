package so2.comunidade.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import so2.comunidade.dados.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, String> {

    Users findByUsername(String username);
}
