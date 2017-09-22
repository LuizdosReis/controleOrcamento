package br.com.springboot.controleorcamento.controleorcamento.model;

import br.com.springboot.controleorcamento.controleorcamento.converter.LocalDateAttributeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
public class Despesa extends AbstractEntity {

	@NotEmpty(message = "A descrição não pode ser vazia")
	private String descricao;
	
	@NotNull
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Convert(converter = LocalDateAttributeConverter.class)
	private LocalDate data;
	
	@Digits(fraction=2,message="O valor só pode conter dois digitos após a virgula",integer = 9)
    @DecimalMin(message = "O Valor não pode ser negativo", value = "0.00", inclusive = false)
	private BigDecimal valor;

	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<DespesaCategorizada> despesasCategorizadas = new HashSet<>();

	@ManyToOne
	@JoinTable(name = "conta_despesa", joinColumns = @JoinColumn(name = "despesa_id"),
			inverseJoinColumns = @JoinColumn(name="conta_id"))
	private Conta conta;

	public Despesa(String descricao, LocalDate data, List<DespesaCategorizada> despesasCategorizadas) {
		this.valor = new BigDecimal("0.00");
		despesasCategorizadas.forEach(gastos -> this.valor = this.valor.add(gastos.getValor()));
		this.descricao = descricao;
		this.data = data;
	}

	public Despesa() {
	    this.valor = new BigDecimal("0.00");
	}

    public Despesa(Long id, String descricao, LocalDate data, List<DespesaCategorizada> despesasCategorizadas) {
	    this(descricao,data,despesasCategorizadas);
        this.id = id;
    }

    public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public Set<DespesaCategorizada> getDespesasCategorizadas() {
		return Collections.unmodifiableSet(despesasCategorizadas);
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		conta.adicionaGasto(this);
		this.conta = conta;
	}

	public void adicionaGastoCategorizado(DespesaCategorizada despesaCategorizada) {
		this.valor = this.valor.add(despesaCategorizada.getValor());
		despesasCategorizadas.add(despesaCategorizada);
	}
}
