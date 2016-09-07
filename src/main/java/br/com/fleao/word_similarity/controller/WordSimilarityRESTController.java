package br.com.fleao.word_similarity.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.fleao.word_similarity.services.WordService;

/**
 * Classe responsável por expor os serviços REST da aplicação. 
 * 
 * Os serviços disponibilizados possibilitam o armazenamento de novas palavras, a recuperação das
 * palavras armazenadas e a recuperação de uma lista de palavras similares à uma keyword informada.
 * 
 * @author felipe
 *
 */
@RestController
@RequestMapping("/rest")
public class WordSimilarityRESTController {
	
	@Autowired
	private WordService wordService;
	
	/**
	 * Adiciona uma palavra ao conjunto de palavras armazenadas pela aplicação. 
	 * 
	 * @param word a palavra a ser adicionada
	 * @return boolean indicando o sucesso ou fracasso da operação
	 */
	@RequestMapping(value="/add/{word}", method={RequestMethod.GET, RequestMethod.POST})
    public boolean addWord(@PathVariable(value="word") String word){
		try{
			wordService.storeWord(word);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * Recupera uma lista com todas as palavras atualmente armazenadas pela aplicação.
	 * 
	 * @return conjunto com todas as palavras armazenadas
	 */
	@RequestMapping(value="/listAll", method={RequestMethod.GET, RequestMethod.POST})
    public Set<String> listAllWords(){
		return wordService.getAllStoredWords();
	}
	
}
