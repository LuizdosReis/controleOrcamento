package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.dto.IncomeCreateDto;
import br.com.springboot.controleorcamento.dto.IncomeDto;
import br.com.springboot.controleorcamento.dto.IncomeReturnDto;
import br.com.springboot.controleorcamento.model.Income;
import br.com.springboot.controleorcamento.repository.IncomeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository repository;

    private final AccountService accountService;

    private final CategoryService categoryService;

    private final ModelMapper modelMapper;

    public IncomeServiceImpl(IncomeRepository repository, AccountService accountService, CategoryService categoryService, ModelMapper modelMapper) {
        this.repository = repository;
        this.accountService = accountService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public IncomeReturnDto save(IncomeCreateDto incomeCreateDto) {

        Income income = modelMapper.map(incomeCreateDto.getIncome(), Income.class);

        income.setAccount(accountService.findOne(incomeCreateDto.getAccountId()));

        income.setCategory(categoryService.findBy(incomeCreateDto.getCategoryId()));

        return modelMapper.map(repository.save(income), IncomeReturnDto.class);
    }
}
