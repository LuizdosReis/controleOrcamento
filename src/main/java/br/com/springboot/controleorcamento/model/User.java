package br.com.springboot.controleorcamento.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString(exclude = {"accounts","categories"})
@EqualsAndHashCode(of = {"id"})
@Table(name = "client")
public class User extends AbstractEntity implements UserDetails{

	@NotBlank
	@Column(unique = true)
	private String username;

	@NotBlank
	private String name;

	@NotBlank
	private String password;

	@OneToMany(fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<>();

	@JsonIgnore
    @OneToMany(mappedBy = "user")
	private Set<Account> accounts = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
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

    public void setAccount(Account account) {
	    accounts.add(account);
    }

    public void setCategory(Category category){
        categories.add(category);
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
