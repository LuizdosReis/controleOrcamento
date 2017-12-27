package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.dto.IncomeCreateDto;
import br.com.springboot.controleorcamento.dto.IncomeReturnDto;

public interface IncomeService {
    IncomeReturnDto save(IncomeCreateDto incomeCreateDto);

    IncomeReturnDto findOne(Long id);

}
