package br.com.springboot.controleorcamento.controleorcamento.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

@Entity
public class Role extends AbstractEntity implements GrantedAuthority {

    private String nome;

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getAuthority() {
        return nome;
    }
}

