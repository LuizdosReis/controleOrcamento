package br.com.springboot.controleorcamento.controleorcamento.model;

import br.com.springboot.controleorcamento.controleorcamento.converter.LocalDateAttributeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Gasto extends AbstractEntity {

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
	private List<GastoCategorizado> gastosCategorizados;

	public Gasto(String descricao, LocalDate data, List<GastoCategorizado> gastosCategorizados) {
		this.valor = new BigDecimal("0.00");
		gastosCategorizados.forEach(gastos -> this.valor = this.valor.add(gastos.getValor()));
		this.descricao = descricao;
		this.data = data;
		this.gastosCategorizados = new ArrayList<>(gastosCategorizados);
	}

	public Gasto() {
	    this.valor = new BigDecimal("0.00");
		this.gastosCategorizados = new ArrayList<>();
	}

    public Gasto(Long id, String descricao, LocalDate data, List<GastoCategorizado> gastosCategorizado) {
	    this(descricao,data,gastosCategorizado);
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

	public List<GastoCategorizado> getGastosCategorizados() {
		return new ArrayList<>(gastosCategorizados);
	}

	public void setGastosCategorizados(List<GastoCategorizado> gastosCategorizados) {
		this.gastosCategorizados = new ArrayList<>(gastosCategorizados);
		this.valor = new BigDecimal("0.00");
		gastosCategorizados.forEach(gastos -> this.valor = this.valor.add(gastos.getValor()));

	}

	public void adicionaGastoCategorizado(GastoCategorizado gastoCategorizado) {
		this.valor = this.valor.add(gastoCategorizado.getValor());
		gastosCategorizados.add(gastoCategorizado);
	}
}
