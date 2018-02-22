package br.com.springboot.controleorcamento.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = true,of = {"id"})
public class Expense extends AbstractEntity {

    @NotBlank(message = "The description not be empty")
    private String description;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    private boolean effected;

    @NotNull
    @Digits(fraction=2,message="The value can only contain two digits after the comma",integer = 9)
    @DecimalMin(message = "Value can not be zero or negative", value = "0.00", inclusive = false)
    private BigDecimal value;

    @NotNull(message = "The category not be empty")
    @ManyToOne
    private Category category;

    @NotNull@NotNull(message = "The account not be empty")
    @ManyToOne
    private Account account;
}
