package br.com.springboot.controleorcamento.controleorcamento.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sun.swing.BakedArrayList;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Usuario extends AbstractEntity implements UserDetails{

	@NotEmpty
	@Column(unique = true)
	private String username;

	@NotEmpty
	private String nome;

	@NotEmpty
	private String password;

	@Transient
	private String ConfirmPassword;

	@OneToMany(fetch = FetchType.EAGER)
	private List<Role> permissoes = new ArrayList<>();

	private boolean admin;

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Conta> contas;

    public Usuario(String username, String password, List<GrantedAuthority> grantedAuthorities) {
        super();
    }

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

    public void setUsername(String username) {
		this.username = username;
	}

    public List<Role> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Role> permissoes) {
        this.permissoes = permissoes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissoes;
    }

    @Override
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

    public List<Conta> getContas() {
        return contas;
    }

    public void setConta(Conta conta) {
	    contas.add(conta);
    }
}
