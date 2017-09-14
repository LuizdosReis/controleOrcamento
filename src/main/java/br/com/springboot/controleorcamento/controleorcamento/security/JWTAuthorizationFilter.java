package br.com.springboot.controleorcamento.controleorcamento.security;

import br.com.springboot.controleorcamento.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.controleorcamento.service.UsuarioService;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static br.com.springboot.controleorcamento.controleorcamento.security.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter  {

    private final UsuarioService usuarioService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UsuarioService usuarioService) {
        super(authenticationManager);
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
         String header = request.getHeader(HEADER_STRING);

         if(header == null || !header.startsWith(TOKEN_PREFIX)){
             chain.doFilter(request,response);
             return;
         }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);
    }



    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token == null)
            return null;

        String user = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

        Usuario usuario = usuarioService.loadUserByUsername(user);

        return user != null ?
                new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()) : null;

    }
}
