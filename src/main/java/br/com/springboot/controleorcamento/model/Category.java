package br.com.springboot.controleorcamento.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true,exclude = {"user","type","description"})
@ToString(callSuper = true)
public class Category extends AbstractEntity {

    @NotBlank(message = "The description not be empty")
    private String description;

    @NotNull(message = "User not be empty")
    @ManyToOne
    private User user;

    @NotNull(message = "Type not be empty")
    @Enumerated(value = EnumType.STRING)
    private Type type;

    @Builder
    private Category(long id, String description, User user, Type type){
        super(id);
        this.description = description;
        this.user = user;
        this.type = type;
    }

}
