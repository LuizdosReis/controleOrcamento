package br.com.springboot.controleorcamento.controleorcamento.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Categoria extends AbstractEntity {

    @NotEmpty(message = "A descrição não pode estar em branco")
    private String descricao;

    @NotNull(message = "Usuario não pode ser nulo")
    @JsonIgnore
    @ManyToOne
    @JoinTable(name = "usuario_categoria", joinColumns = @JoinColumn(name = "categoria_id"),
            inverseJoinColumns = @JoinColumn(name="usuario_id"))
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria")
    private Set<Despesa> despesas = new HashSet<>();


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

    public Set<Despesa> getDespesas() {
        return Collections.unmodifiableSet(despesas);
    }

    public void adicionaDespesa(Despesa despesa){
        this.despesas.add(despesa);
    }
}
