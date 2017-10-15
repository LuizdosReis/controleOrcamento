package br.com.springboot.controleorcamento.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Categoria extends AbstractEntity {

    @NotEmpty(message = "A descrição não pode estar em branco")
    private String descricao;

    @NotNull(message = "Usuario não pode ser nulo")
    @JsonIgnore
    @ManyToOne
    private Usuario usuario;

    @NotNull(message = "Tipo nao pode ser nulo")
    @Enumerated(value = EnumType.STRING)
    private Tipo tipo;
}
