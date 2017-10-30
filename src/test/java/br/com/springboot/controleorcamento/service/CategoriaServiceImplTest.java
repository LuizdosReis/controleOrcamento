package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.dto.CategoriaDto;
import br.com.springboot.controleorcamento.model.Categoria;
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
import static org.mockito.Mockito.*;


public class CategoriaServiceImplTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ModelMapper modelMapper;


    private CategoriaService categoriaService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        categoriaService = new CategoriaServiceImpl(categoriaRepository, modelMapper);
    }

//    @Test
//    public void deveSalvarCategoria() throws Exception {
//        Usuario luiz = new Usuario();
//        luiz.setNome("Luiz Henrique");
//        luiz.setUsername("luiz.reis");
//        luiz.setPassword("123");
//
//        Categoria carro = new Categoria();
//        carro.setDescricao("Carro");
//        carro.setTipo(Tipo.SAIDA);
//
//        CategoriaDto carroDto = new CategoriaDto();
//        carroDto.setDescricao("Carro");
//        carro.setTipo(Tipo.SAIDA);
//
//        when(categoriaRepository.save(carro)).thenReturn(carro);
//
//        when(modelMapper.map(carroDto,Categoria.class)).thenReturn(carro);
//
//        CategoriaDto categoriaDtoRetornada = categoriaService.save(carroDto, luiz);
//
//        assertThat(categoriaDtoRetornada.getDescricao()).isEqualTo(carro.getDescricao());
//        assertThat(categoriaDtoRetornada.getTipo()).isEqualTo(carro.getTipo());
//        verify(categoriaRepository,times(1)).save(carro);
//    }

//    @Test
//    public void naoDeveSalvarCategoriaSemUsuario() throws Exception {
//        thrown.expect(ConstraintViolationException.class);
//
//        Categoria carro = new Categoria();
//        carro.setDescricao("Carro");
//
//        when(categoriaRepository.save(carro)).thenThrow(ConstraintViolationException.class);
//
//        categoriaService.save(carro,null);
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
//        List<CategoriaInfo> categorias = categoriaService.findByUsuario(luiz, new PageRequest(0,20)).getContent();
//
//        assertThat(categorias.size()).isEqualTo(1);
//        assertThat(categorias.get(0)).isEqualTo(categoriaInfo);
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