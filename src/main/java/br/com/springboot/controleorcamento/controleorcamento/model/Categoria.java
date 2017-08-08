package br.com.springboot.controleorcamento.controleorcamento.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;

@Entity
public class Categoria extends AbstractEntity {

    @NotEmpty
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
