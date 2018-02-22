package br.com.springboot.controleorcamento.repository;

import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {

    Page<Account> findByUser(User user, Pageable pageable);

}