package br.com.springboot.controleorcamento.controleorcamento;


import br.com.springboot.controleorcamento.controleorcamento.converter.LocalDateConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.time.LocalDate;

@Controller
@SpringBootApplication
public class ControleOrcamentoApplication extends WebMvcConfigurerAdapter{
	public static void main(String[] args) {
	    SpringApplication.run(ControleOrcamentoApplication.class, args);
	}
	

	@Bean
    @Primary
    public ObjectMapper serializingObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            javaTimeModule.addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE);
            javaTimeModule.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LocalDateConverter("dd/MM/yyyy"));
    }


    @GetMapping("/home")
    public String index(){
	    return "index";
    }
}
