package br.com.springboot.controleorcamento.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;


@Entity
@Data
@EqualsAndHashCode(of = {"id"})
public class Role extends AbstractEntity implements GrantedAuthority {

    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}

