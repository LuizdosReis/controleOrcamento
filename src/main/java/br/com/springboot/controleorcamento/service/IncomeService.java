package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.dto.IncomeCreateDto;
import br.com.springboot.controleorcamento.dto.IncomeDto;
import br.com.springboot.controleorcamento.dto.IncomeReturnDto;
import br.com.springboot.controleorcamento.model.Income;

public interface IncomeService {
    IncomeReturnDto save(IncomeCreateDto incomeCreateDto);

    IncomeReturnDto findOne(Long id);

}
