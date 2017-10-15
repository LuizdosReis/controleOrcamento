package br.com.springboot.controleorcamento.repository;

import br.com.springboot.controleorcamento.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,Long>{
    Usuario findByUsername(String username);
}
