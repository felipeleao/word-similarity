package br.com.fleao.word_similarity.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.fleao.word_similarity.services.WordService;

@RestController
@RequestMapping("/rest")
public class WordSimilarityRESTController {

	@Autowired
	private WordService wordService;
	
	@RequestMapping(value="/add/{word}", method={RequestMethod.GET, RequestMethod.POST})
    public boolean addWord(@PathVariable(value="word") String word){
		wordService.storeWord(word);
		return true;
	}
	
	@RequestMapping(value="/listAll", method={RequestMethod.GET, RequestMethod.POST})
    public Set<String> listAllWords(){
		return wordService.getAllStoredWords();
	}
	
}
