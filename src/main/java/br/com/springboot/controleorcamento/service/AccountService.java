package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.dto.AccountCreateDto;
import br.com.springboot.controleorcamento.dto.AccountDto;
import br.com.springboot.controleorcamento.dto.AccountUpdateDto;
import br.com.springboot.controleorcamento.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    Page<AccountDto> findAll(Pageable pageable);

    AccountDto save(AccountCreateDto accountCreateDto);

    AccountDto findOneDto(Long id);

    Account findOne(Long id);

    void update(AccountUpdateDto account);

    void delete(long id);
}
