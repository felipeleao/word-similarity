package br.com.fleao.word_similarity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe para inicialização da aplicação através do Spring Boot. 
 * 
 * Esta classe classe comporta os métodos básicos para a incialização da aplicação, invocando o SpringBoot
 * para que todas as APIs subjacentes sejam incializadas e injeções necessárias realizadas.
 * 
 * @author Felipe Leão
 * @since 0.0.1-SNAPSHOT
 */
@SpringBootApplication
public class Runner {
	
	/**
	 * Método utilizado para inicializar a aplicação quando o JAR é invocado.
	 * 
	 * @param args
	 */
    public static void main( String[] args ){
    	SpringApplication.run(Runner.class, args);
    	System.out.println("Inicializado");
    }
}
