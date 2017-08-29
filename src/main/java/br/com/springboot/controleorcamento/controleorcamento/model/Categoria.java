package br.com.springboot.controleorcamento.controleorcamento.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Categoria extends AbstractEntity {

    @NotEmpty(message = "A descrição não pode estar em branco")
    private String descricao;

    public Categoria(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Categoria(String descricao){
        this.descricao = descricao;
    }

    public Categoria() {
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
