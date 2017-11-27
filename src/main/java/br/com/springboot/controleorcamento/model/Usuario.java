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
@ToString(exclude = {"accounts","categories"})
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
	private Set<Account> contas = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private Set<Category> categories = new HashSet<>();

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

    public void setConta(Account conta) {
	    contas.add(conta);
    }

    public void setCategoria(Category category){
        categories.add(category);
    }

    public Set<Account> getContas() {
        return contas;
    }

    @Transactional
    public Set<Role> getRoles() {
        return roles;
    }

    @Transactional
    public Set<Category> getCategories() {
        return categories;
    }


}
