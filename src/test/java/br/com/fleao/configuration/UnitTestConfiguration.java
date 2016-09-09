package br.com.fleao.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.fleao.word_similarity.services.WordService;

/**
 * Configuração de beans para casos de teste.
 *  
 * @author felipe
 *
 */
@Configuration
public class UnitTestConfiguration {

	@Bean
	public WordService wordService() {
		return new WordService();
	}

}