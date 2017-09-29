package br.com.springboot.controleorcamento.controleorcamento.repository;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Usuario;
import org.assertj.core.api.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestEntityManager entityManager;


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void salvaCategoria(){
        Usuario luiz = new Usuario();
        luiz.setNome("Luiz Henrique");
        luiz.setUsername("luiz.reis");
        luiz.setPassword("123");

        luiz = usuarioRepository.save(luiz);

        Categoria categoria = new Categoria();
        categoria.setUsuario(luiz);
        categoria.setDescricao("Carro");

        Categoria categoriaRetornada = categoriaRepository.save(categoria);


        assertThat(categoriaRetornada.getDescricao()).isEqualTo(categoria.getDescricao());
        assertThat(categoriaRetornada.getUsuario()).isEqualTo(categoria.getUsuario());

    }

    public void deveRetornoSomenteCategoriasDoUsuario(){
        Usuario luiz = new Usuario();
        luiz.setNome("Luiz Henrique");
        luiz.setUsername("luiz.reis");
        luiz.setPassword("123");

        Usuario jose = new Usuario();
        jose.setNome("Jose silva");
        jose.setUsername("jose.silva");
        jose.setPassword("123");

        luiz = usuarioRepository.save(luiz);
        jose = usuarioRepository.save(jose);

        Categoria carro = new Categoria();
        carro.setUsuario(luiz);
        carro.setDescricao("Carro");

        Categoria moto = new Categoria();
        moto.setUsuario(jose);
        moto.setDescricao("Carro");

        categoriaRepository.save(moto);
        categoriaRepository.save(carro);

        categoriaRepository.f

    }




}