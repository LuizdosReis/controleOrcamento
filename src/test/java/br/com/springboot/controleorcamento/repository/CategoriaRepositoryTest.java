//package br.com.springboot.controleorcamento.repository;
//
//import br.com.springboot.controleorcamento.model.Category;
//import br.com.springboot.controleorcamento.model.Tipo;
//import br.com.springboot.controleorcamento.model.Usuario;
//import br.com.springboot.controleorcamento.projections.CategoriaInfo;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//public class CategoriaRepositoryTest {
//
//    @Autowired
//    private CategoriaRepository categoriaRepository;
//
//    @Autowired
//    private UsuarioRepository usuarioRepository;
//
//    @Rule
//    public ExpectedException thrown = ExpectedException.none();
//
//    @Test
//    public void salvaCategoria(){
//        Usuario luiz = new Usuario();
//        luiz.setNome("Luiz Henrique");
//        luiz.setUsername("luiz.henrique");
//        luiz.setPassword("123");
//
//        luiz = usuarioRepository.save(luiz);
//
//        Category category = new Category();
//        category.setUsuario(luiz);
//        category.setDescricao("Carro");
//
//        Category categoriaRetornada = categoriaRepository.save(category);
//
//
//        assertThat(categoriaRetornada.getDescricao()).isEqualTo(category.getDescricao());
//        assertThat(categoriaRetornada.getUsuario()).isEqualTo(category.getUsuario());
//
//    }
//
//    @Test
//    public void deveRetornoSomenteCategoriasDoUsuario(){
//        Usuario luiz = new Usuario();
//        luiz.setNome("Luiz Henrique");
//        luiz.setUsername("luiz.henrique");
//        luiz.setPassword("123");
//
//        Usuario jose = new Usuario();
//        jose.setNome("Jose silva");
//        jose.setUsername("jose.silva");
//        jose.setPassword("123");
//
//        luiz = usuarioRepository.save(luiz);
//        jose = usuarioRepository.save(jose);
//
//        Category carro = new Category();
//        carro.setUsuario(luiz);
//        carro.setDescricao("Carro");
//        carro.setTipo(Tipo.SAIDA);
//
//        Category moto = new Category();
//        moto.setUsuario(jose);
//        moto.setDescricao("Moto");
//        moto.setTipo(Tipo.SAIDA);
//
//        categoriaRepository.save(moto);
//        categoriaRepository.save(carro);
//
//        List<CategoriaInfo> categories = categoriaRepository.findByUsuario(luiz,new PageRequest(0, 20)).getContent();
//
//        assertThat(categories.size()).isEqualTo(1);
//        assertThat(categories.get(0).getDescricao()).isEqualTo(carro.getDescricao());
//        assertThat(categories.get(0).getId()).isEqualTo(carro.getId());
//        assertThat(categories.get(0).getTipo()).isEqualTo(carro.getTipo());
//    }
//
//
//
//
//}