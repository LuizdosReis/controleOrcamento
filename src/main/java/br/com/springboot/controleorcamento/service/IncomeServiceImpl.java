package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.dto.IncomeCreateDto;
import br.com.springboot.controleorcamento.dto.IncomeReturnDto;
import br.com.springboot.controleorcamento.model.Income;
import br.com.springboot.controleorcamento.repository.IncomeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository repository;

    private final AccountService accountService;

    private final CategoryService categoryService;

    private final UserService userService;

    private final ModelMapper modelMapper;
    private String NO_INCOME_FOUND_BY_ID = "No Income Found by ID ";

    public IncomeServiceImpl(IncomeRepository repository, AccountService accountService, CategoryService categoryService, UserService userService, ModelMapper modelMapper) {
        this.repository = repository;
        this.accountService = accountService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public IncomeReturnDto save(IncomeCreateDto incomeCreateDto) {

        Income income = modelMapper.map(incomeCreateDto.getIncome(), Income.class);

        income.setAccount(accountService.findOne(incomeCreateDto.getAccountId()));

        income.setCategory(categoryService.findBy(incomeCreateDto.getCategoryId()));

        return modelMapper.map(repository.save(income), IncomeReturnDto.class);
    }

    @Override
    public IncomeReturnDto findOne(Long id) {
        Income income = repository.findByIdAndAccount_User(id, userService.getCurrentUser())
                .orElseThrow(() -> {
                    log.error(NO_INCOME_FOUND_BY_ID + id.toString());
                    return new ResourceNotFoundException(NO_INCOME_FOUND_BY_ID + id.toString(), null);
                });

        return modelMapper.map(income, IncomeReturnDto.class);
    }
}
