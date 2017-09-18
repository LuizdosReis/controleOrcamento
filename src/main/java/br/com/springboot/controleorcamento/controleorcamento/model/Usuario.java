package br.com.springboot.controleorcamento.controleorcamento.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;

@Entity
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

    @OneToMany(mappedBy = "usuario")
	private Set<Conta> contas = new HashSet<>();

    public String getUsername() {
		return username;
	}

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

	public void setPassword(String password) {
		this.password = password;
	}

    public void setUsername(String username) {
        this.username = username;
    }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

    public Set<Conta> getContas() {
        return contas;
    }

    public void setConta(Conta conta) {
	    contas.add(conta);
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
