package br.com.springboot.controleorcamento.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true,exclude = {"usuario","tipo","descricao"})
@ToString(callSuper = true)
public class Category extends AbstractEntity {

    @Builder
    private Category(long id, String descricao,Usuario usuario, Tipo tipo){
        super(id);
        this.descricao = descricao;
        this.usuario = usuario;
        this.tipo = tipo;
    }

    @NotEmpty(message = "A descrição não pode estar em branco")
    private String descricao;

    @NotNull(message = "Usuario não pode ser nulo")
    @ManyToOne
    private Usuario usuario;

    @NotNull(message = "Tipo nao pode ser nulo")
    @Enumerated(value = EnumType.STRING)
    private Tipo tipo;
}
