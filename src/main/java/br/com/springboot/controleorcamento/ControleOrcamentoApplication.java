package br.com.springboot.controleorcamento;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@SpringBootApplication
@EnableTransactionManagement
public class ControleOrcamentoApplication extends SpringBootServletInitializer {
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

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder bcyrpt() {
        return new BCryptPasswordEncoder();
    }

    @RequestMapping({"","/","index"})
    public String index(){
	    return "index";
    }
}
