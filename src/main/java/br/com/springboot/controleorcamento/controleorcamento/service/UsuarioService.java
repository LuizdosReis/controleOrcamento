package br.com.springboot.controleorcamento.controleorcamento.service;

import br.com.springboot.controleorcamento.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.controleorcamento.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService{

    private final UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Usuario save(Usuario usuario){
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = Optional.ofNullable(usuarioRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER","ROLE_ADMIN","ROLE_ACTUATOR");
        List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
        return new Usuario(
                user.getUsername(),
                user.getPassword(),
                user.isAdmin() ? authorityListAdmin : authorityListUser);
    }
}
