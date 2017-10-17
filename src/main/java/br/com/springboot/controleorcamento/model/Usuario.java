package br.com.springboot.controleorcamento.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@ToString(exclude = {"contas","categorias"})
@EqualsAndHashCode(of = {"id"})
public class Usuario extends AbstractEntity implements UserDetails{

	@NotEmpty
	@Column(unique = true)
	private String username;

	@NotEmpty
	private String nome;

	@NotEmpty
	private String password;

	@OneToMany(fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<>();

	@JsonIgnore
    @OneToMany(mappedBy = "usuario")
	private Set<Conta> contas = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private Set<Categoria> categorias = new HashSet<>();

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableSet(roles);
    }

    @Override
    public String getPassword() {
		return password;
	}

    public void setConta(Conta conta) {
	    contas.add(conta);
    }

    public void setCategoria(Categoria categoria){
        categorias.add(categoria);
    }

    public Set<Conta> getContas() {
        return contas;
    }

    @Transactional
    public Set<Role> getRoles() {
        return roles;
    }

    @Transactional
    public Set<Categoria> getCategorias() {
        return categorias;
    }


}
