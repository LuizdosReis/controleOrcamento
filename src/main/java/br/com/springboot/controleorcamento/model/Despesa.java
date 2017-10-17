package br.com.springboot.controleorcamento.model;

import br.com.springboot.controleorcamento.converter.LocalDateAttributeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
public class Despesa extends AbstractEntity {

    @NotEmpty(message = "A descrição não pode ser vazia")
    private String descricao;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate data;

    private boolean efetivada;

    @Digits(fraction=2,message="O valor só pode conter dois digitos após a virgula",integer = 9)
    @DecimalMin(message = "O Valor não pode ser zerado ou negativo", value = "0.00", inclusive = false)
    private BigDecimal valor;

    @NotNull
    @ManyToOne
    private Categoria categoria;

    @NotNull
    @ManyToOne
    private Conta conta;
}
