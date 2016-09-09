package br.com.fleao.word_similarity.services;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.fleao.word_similarity.configuration.UnitTestConfiguration;

/**
 * Testes unitários para verificar o correto funcionamento dos métodos da classe {@link WordService}
 * 
 * @author felipe
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UnitTestConfiguration.class })
@TestPropertySource(value = { "classpath:application.properties" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class WordServiceTest {

	@Autowired
	private WordService wordService;
	
	@Before
	public void setUp() throws Exception {
		// Após cada tete o contexto é destruído e recriado, com os beans sendo reinjetados e singletons reinstanciados (@DirtiesContext)
		Assert.assertNotNull("O contexto do Spring não foi capaz de injetar a dependência 'wordService'.", wordService);
	}

	@After
	public void tearDown() throws Exception {
		// Nada a fazer
	}

	
	/**
	 * verifica se o método {@link WordService#storeWord(String)} está levantando exceções quando recebe 
	 * parâmetros inválidos, conforme esperado.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testStoreWordIllegalArguments(){
		wordService.storeWord(null);
	}
	
	/**
	 * Verifica se uma palavra é adicionada e recuperada corretamente
	 */
	@Test
	public void testStoreWords(){
		//Verifica se o conjunto retornado é vazio
		Assert.assertEquals(new HashSet<String>(), wordService.getAllStoredWords());
		
		// Verifica a inserção comum
		wordService.storeWord("a");
		Assert.assertEquals(new HashSet<String>(Arrays.asList("a")), wordService.getAllStoredWords());
		
	}
	
	/**
	 * Vrifica se mesmo uma palavra tendo sido inserida multiplas vezes, apenas uma cópia é mantida
	 */
	@Test
	public void testRepeatedInsertion(){
		// Verifica a o armazenamento não repetido quando um mesmo valor é inserido múltiplas vezes
		wordService.storeWord("a");
		wordService.storeWord("a");
		wordService.storeWord("a");
		Assert.assertEquals(new HashSet<String>(Arrays.asList("a")), wordService.getAllStoredWords());
	}
	
	/**
	 * verifica se ao inserir múltiplas palavras, todas são recuperadas corretamente.
	 */
	public void testMultipleInsertion(){
		// Verifica se após a inserção de múltiplas palavras, todas são retornadas
		wordService.storeWord("a");
		wordService.storeWord("b");
		wordService.storeWord("c");
		wordService.storeWord("d");
		Assert.assertEquals(new HashSet<String>(Arrays.asList("a","b","c","d")), wordService.getAllStoredWords());
	}
	
	/**
	 * Mesmo quando nenhuma palavra foi adicionada, o método {@link WordService#getAllStoredWords()} não deve retornar null.
	 */
	@Test
	public void testGetAllStoredWordsNotNull(){
		
		Assert.assertNotNull("O método getAllStoredWords não deveria retornar NULL mesmo quando não há palavras armazenadas.", 
				wordService.getAllStoredWords());
		
		Assert.assertEquals("Logo após a inicialização o serviço não deveria ter nenhuma palavra armazenada", 
				new HashSet<String>(), wordService.getAllStoredWords());

		wordService.storeWord("a");
		Assert.assertNotNull("O método getAllStoredWords não deveria retornar NULL após a inserção de um valor.", 
				wordService.getAllStoredWords());
	}
	
	@Test
	public void testListSimilarStoredWords(){
		wordService.storeWord("manhã");
		wordService.storeWord("tarde");
		wordService.storeWord("noite");
		wordService.storeWord("abacate");
		
		Assert.assertEquals(new HashSet<String>(Arrays.asList("manhã")), wordService.listSimilarStoredWords("manhã", 0));
		Assert.assertEquals(new HashSet<String>(Arrays.asList("abacate","noite")), wordService.listSimilarStoredWords("boate", 3));
		
		Assert.assertEquals(new HashSet<String>(), wordService.listSimilarStoredWords("banana", 3));
		Assert.assertEquals(new HashSet<String>(Arrays.asList("abacate","manhã")), wordService.listSimilarStoredWords("banana", 4));
		
		Assert.assertNotEquals(new HashSet<String>(Arrays.asList("manhã")), wordService.listSimilarStoredWords("manha", 0));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testListSimilarStoredWOrdsIllegalKeyword(){
		wordService.listSimilarStoredWords(null, 0);
	}

}
