package br.com.springboot.controleorcamento.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true,exclude = {"description","value","received","date","account","category"})
@ToString(callSuper = true)
public class Income extends AbstractEntity{

    @Builder
    private Income(long id, String description,
                   Account account,
                   BigDecimal value,
                   LocalDate date,
                   boolean received,
                   Category category){
        super(id);
        this.description = description;
        this.account = account;
        this.value = value;
        this.date = date;
        this.received = received;
        this.category = category;
    }

    @NotBlank(message = "The description can not be empty")
    private String description;

    @Digits(fraction=2,message="The value can only contain two digits after the comma",integer = 14)
    @DecimalMin(message = "The value can not be zeroed or negative", value = "0.00", inclusive = false)
    private BigDecimal value;

    private boolean received;

    @NotNull(message = "The date can not be null")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    @NotNull(message = "The account can not be null")
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    private Account account;

    @NotNull(message = "The category can not be null")
    @ManyToOne
    private Category category;
}
