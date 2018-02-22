package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.dto.AccountCreateDto;
import br.com.springboot.controleorcamento.dto.AccountDto;
import br.com.springboot.controleorcamento.dto.AccountUpdateDto;
import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public AccountServiceImpl(AccountRepository accountRepository, UserService userService, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<AccountDto> findAll(Pageable pageable) {
        log.debug("CategoryService - findAll");

        Page<Account> page = accountRepository.findByUser(userService.getCurrentUser(), pageable);

        List<AccountDto> accountDtos = page.getContent().stream()
                .map(a -> modelMapper.map(a, AccountDto.class)).collect(Collectors.toList());

        return new PageImpl<>(accountDtos, pageable, page.getTotalElements());
    }

    @Override
    public AccountDto save(AccountCreateDto accountCreateDto) {
        log.debug("CategoryService - save");

        Account account = modelMapper.map(accountCreateDto, Account.class);

        account.setUser(userService.getCurrentUser());
        return modelMapper.map(accountRepository.save(account), AccountDto.class);
    }

    @Override
    public AccountDto findOneDto(Long id) {
        log.debug("Service - findOneDto");
        return modelMapper.map(getAccount(id), AccountDto.class);
    }

    @Override
    public Account findOne(Long id) {
        log.debug("Service - findOne");
        return getAccount(id);
    }

    private Account getAccount(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);

        if (!accountOptional.isPresent()) {
            log.error("Service - no account found with id");
            throw new ResourceNotFoundException("no account found with id", null);
        }

        return accountOptional.get();
    }


    @Override
    public void update(AccountUpdateDto account) {
        checkIfAccountExists(account.getId());
        accountRepository.save(modelMapper.map(account, Account.class));
    }

    @Override
    public void delete(long id) {
        checkIfAccountExists(id);
        accountRepository.deleteById(id);
    }

    private void checkIfAccountExists(Long id) {
        if (!accountRepository.existsById(id))
            throw new ResourceNotFoundException("no account found with id", null);
    }
}
