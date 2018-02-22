package br.com.springboot.controleorcamento.repository;

import br.com.springboot.controleorcamento.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long>{
    User findByUsername(String username);
}
