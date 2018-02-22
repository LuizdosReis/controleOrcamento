package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.helper.CategoryHelper;
import br.com.springboot.controleorcamento.model.Category;
import br.com.springboot.controleorcamento.model.User;
import br.com.springboot.controleorcamento.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;


    private CategoryService categoryService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryServiceImpl(categoryRepository, userService, modelMapper);
    }

    @Test
    public void deveSalvarCategoria() throws Exception {
        User luiz = new User();
        luiz.setName("Luiz Henrique");
        luiz.setUsername("luiz.reis");
        luiz.setPassword("123");

        Category carro = CategoryHelper.buildCategory();
        carro.setUser(luiz);

        when(categoryRepository.save(carro)).thenReturn(carro);
        when(userService.getCurrentUser()).thenReturn(luiz);

        Category categorySaved = categoryService.save(carro);

        assertThat(categorySaved.getDescription()).isEqualTo(carro.getDescription());
        assertThat(categorySaved.getType()).isEqualTo(carro.getType());
        assertThat(categorySaved.getUser()).isEqualTo(luiz);
        verify(categoryRepository,times(1)).save(carro);
        verify(userService,times(1)).getCurrentUser();
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
//    public void findByUser() throws Exception {
//        User luiz = new User();
//        luiz.setName("Luiz Henrique");
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
//            public Type getType() {
//                return Type.SAIDA;
//            }
//        };
//
//
//        when(categoriaRepository.findByUser(luiz,new PageRequest(0,20))).thenReturn(new PageImpl<>(Collections.singletonList(categoriaInfo)));
//
//        List<CategoriaInfo> categories = categoryService.findByUser(luiz, new PageRequest(0,20)).getContent();
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