package br.com.springboot.controleorcamento.controleorcamento.security;

public class SecurityConstants {

    static final long EXPIRATION_TIME = 86400000L;
    static final String SECRET = "ControleOrcamento";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String SIGN_UP_URL = "/login";
}
