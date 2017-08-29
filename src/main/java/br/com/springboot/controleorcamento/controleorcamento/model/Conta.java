package br.com.springboot.controleorcamento.controleorcamento.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Conta extends AbstractEntity{

    @NotEmpty(message = "A descrição não pode ser vazia")
    private String descricao;

    @NotNull
    @Digits(fraction=2,message="O valor só pode conter dois digitos após a virgula",integer = 9)
    private BigDecimal saldo;

    @OneToMany
    private List<Gasto> gastos;

    @OneToMany
    private List<Receita> receitas;

    public Conta() {
        gastos = new ArrayList<>();
        receitas = new ArrayList<>();
    }

    private Conta(Builder builder) {
        setId(builder.id);
        setDescricao(builder.descricao);
        setSaldo(builder.saldo);
        gastos = new ArrayList<>();
        receitas = new ArrayList<>();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void adicionaGasto(Gasto gasto){
        this.saldo = this.saldo.subtract(gasto.getValor());
        this.gastos.add(gasto);
    }

    public void adicionaReceita(Receita receita){
        this.saldo = this.saldo.add(receita.getValor());
        this.receitas.add(receita);
    }

    public List<Gasto> getGastos() {
        return  new ArrayList<>(gastos);
    }

    public List<Receita> getReceitas() {
        return new ArrayList<>(receitas);
    }


    public static final class Builder {
        private Long id;
        private String descricao;
        private BigDecimal saldo;
        private List<Gasto> gastos;
        private List<Receita> receitas;

        public Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder descricao(String val) {
            descricao = val;
            return this;
        }

        public Builder saldo(BigDecimal val) {
            saldo = val;
            return this;
        }


        public Conta build() {
            return new Conta(this);
        }
    }
}
