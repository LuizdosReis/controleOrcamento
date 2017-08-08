package br.com.springboot.controleorcamento.controleorcamento.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.springboot.controleorcamento.controleorcamento.converter.LocalDateAttributeConverter;

@Entity
public class Gasto extends AbstractEntity {

	@NotEmpty
	private String descricao;
	
	@NotNull
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Convert(converter = LocalDateAttributeConverter.class)
	private LocalDate data;
	
	@Digits(fraction=2,message="O valor so pode conter dois digitos após a virgula",integer = 9)
	@DecimalMin(value="0.00", inclusive=false)
	private BigDecimal valor;

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<GastoCategorizado> gastosCategorizados;

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

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public List<GastoCategorizado> getGastosCategorizados() {
		return gastosCategorizados;
	}

	public void setGastosCategorizados(List<GastoCategorizado> gastosCategorizados) {
		this.gastosCategorizados = gastosCategorizados;
	}
}
