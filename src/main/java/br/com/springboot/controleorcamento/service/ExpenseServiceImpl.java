package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.model.Category;
import br.com.springboot.controleorcamento.model.Expense;
import br.com.springboot.controleorcamento.model.User;
import br.com.springboot.controleorcamento.repository.ExpenseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final AccountService accountService;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository,
                              CategoryService categoryService,
                              UserService userService,
                              AccountService accountService) {
        this.expenseRepository = expenseRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public Expense save(Expense expense) {

        log.debug("Method save");

        Account account = accountService.findOne(expense.getAccount().getId());

        return addExpense(expense,account);
    }

    @Override
    public void update(Expense expense) {
        verifyIfExpenseExist(expense.getId());

        Account account = accountService.findOne(expense.getAccount().getId());

        account.getExpenses().stream()
                .filter(d -> d.getId().equals(expense.getId()))
                .findFirst()
                .ifPresent(d -> account.remove(d));

        addExpense(expense,account);
    }

    private Expense addExpense(Expense expense, Account account) {
        expense.setCategory(categoryService.findBy(expense.getCategory().getId()));

        account.add(expense);

        expense.setAccount(account);
        return expenseRepository.save(expense);
    }


    @Override
    public void delete(long id) {
        verifyIfExpenseExist(id);
        expenseRepository.deleteById(id);
    }

    @Override
    public Page<Expense> findAll(User user, Pageable pageable) {
        user = userService.loadUserByUsername(user.getUsername());

        return expenseRepository.findAllByAccountIn(user.getAccounts(),pageable);
    }

    @Override
    public Expense getById(Long id) {
        verifyIfExpenseExist(id);
        return expenseRepository.findById(id).get();
    }

    @Override
    public Page<Expense> findByCategory(long idCategoria, Pageable pageable) {
        Category category = categoryService.findBy(idCategoria);
        return expenseRepository.findByCategory(category,pageable);
    }

    @Override
    public Page<Expense> findByDataBetween(LocalDate dataInicial, LocalDate dataFinal, Pageable pageable) {
        return expenseRepository.findByDateBetween(dataInicial,dataFinal,pageable);
    }

    private void verifyIfExpenseExist(Long id) {
        if (expenseRepository.existsById(id))
            throw new ResourceNotFoundException("No expense found in id", null);
    }
}
