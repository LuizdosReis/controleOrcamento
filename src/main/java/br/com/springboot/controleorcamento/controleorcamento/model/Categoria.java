package br.com.springboot.controleorcamento.controleorcamento.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Categoria extends AbstractEntity {

    @NotEmpty(message = "A descrição não pode estar em branco")
    private String descricao;

    @NotNull(message = "Usuario não pode ser nulo")
    @JsonIgnore
    @ManyToOne
    private Usuario usuario;

    @Enumerated(value = EnumType.STRING)
    private Tipo tipo;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
