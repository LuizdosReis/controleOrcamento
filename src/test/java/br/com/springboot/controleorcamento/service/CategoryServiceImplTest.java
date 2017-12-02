package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.helper.CategoryHelper;
import br.com.springboot.controleorcamento.model.Category;
import br.com.springboot.controleorcamento.model.Tipo;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.repository.CategoriaRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class CategoryServiceImplTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private ModelMapper modelMapper;


    private CategoryService categoryService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryServiceImpl(categoriaRepository, usuarioService, modelMapper);
    }

    @Test
    public void deveSalvarCategoria() throws Exception {
        Usuario luiz = new Usuario();
        luiz.setNome("Luiz Henrique");
        luiz.setUsername("luiz.reis");
        luiz.setPassword("123");

        Category carro = CategoryHelper.buildCategory();
        carro.setUsuario(luiz);

        when(categoriaRepository.save(carro)).thenReturn(carro);
        when(usuarioService.getCurrentUser()).thenReturn(luiz);

        Category categorySaved = categoryService.save(carro);

        assertThat(categorySaved.getDescricao()).isEqualTo(carro.getDescricao());
        assertThat(categorySaved.getTipo()).isEqualTo(carro.getTipo());
        assertThat(categorySaved.getUsuario()).isEqualTo(luiz);
        verify(categoriaRepository,times(1)).save(carro);
        verify(usuarioService,times(1)).getCurrentUser();
    }

//    @Test
//    public void naoDeveSalvarCategoriaSemUsuario() throws Exception {
//        thrown.expect(ConstraintViolationException.class);
//
//        Category carro = new Category();
//        carro.setDescricao("Carro");
//
//        when(categoriaRepository.save(carro)).thenThrow(ConstraintViolationException.class);
//
//        categoryService.save(carro,null);
//    }
//
//    @Test
//    public void findByUsuario() throws Exception {
//        Usuario luiz = new Usuario();
//        luiz.setNome("Luiz Henrique");
//        luiz.setUsername("luiz.reis");
//        luiz.setPassword("123");
//
//        CategoriaInfo categoriaInfo = new CategoriaInfo() {
//            @Override
//            public Long getId() {
//                return 1L;
//            }
//
//            @Override
//            public String getDescricao() {
//                return "Carro";
//            }
//
//            @Override
//            public Tipo getTipo() {
//                return Tipo.SAIDA;
//            }
//        };
//
//
//        when(categoriaRepository.findByUsuario(luiz,new PageRequest(0,20))).thenReturn(new PageImpl<>(Collections.singletonList(categoriaInfo)));
//
//        List<CategoriaInfo> categories = categoryService.findByUsuario(luiz, new PageRequest(0,20)).getContent();
//
//        assertThat(categories.size()).isEqualTo(1);
//        assertThat(categories.get(0)).isEqualTo(categoriaInfo);
//
//    }
//
//    @Test
//    public void update() throws Exception {
//    }
//
//    @Test
//    public void findOne() throws Exception {
//    }
//
//    @Test
//    public void verificaSeCategoriasPertencemAoUsuario() throws Exception {
//    }

}