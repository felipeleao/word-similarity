package br.com.fleao.word_similarity.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.fleao.word_similarity.configuration.UnitTestConfiguration;

/**
 * Classe de teste para validar os métodos publicos da classe LevenshteinDistanceAlgorithm
 * 
 * @author felipe
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UnitTestConfiguration.class })
@TestPropertySource(value = { "classpath:application.properties" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class LevenshteinDistanceAlgorithmTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Quando somente uma inserção é feita, a distância tem que ser igual a 1
	 */
	@Test
	public void testDistanceSimpleInsertion() {
		Assert.assertEquals(1, LevenshteinDistanceAlgorithm.getDistance("abc", "abcd"));
		Assert.assertEquals(1, LevenshteinDistanceAlgorithm.getDistanceEfficiently("abc", "abcd"));
	}
	
	/**
	 * Quando somente uma remoção é feita, a distância tem que ser igual a 1
	 */
	@Test
	public void testDistanceSimpleRemotion() {
		Assert.assertEquals(1, LevenshteinDistanceAlgorithm.getDistance("abcd", "abc"));
		Assert.assertEquals(1, LevenshteinDistanceAlgorithm.getDistanceEfficiently("abcd", "abc"));
	}
	/**
	 * Quando somente uma substituição é feita, a distância tem que ser igual a 1
	 */
	@Test
	public void testDistanceSimpleSubstitution() {
		Assert.assertEquals(1, LevenshteinDistanceAlgorithm.getDistance("abc", "ab1"));
		Assert.assertEquals(1, LevenshteinDistanceAlgorithm.getDistanceEfficiently("abc", "ab1"));
	}
	
	/**
	 * Quando uma operação de inserção, uma de substituição e uma de remoção são feitas, a distância deve ser 3
	 */
	@Test
	public void testMixedInsertSubstitutioRemotionCombination() {
		Assert.assertEquals(3, LevenshteinDistanceAlgorithm.getDistance("abc", "0b1d"));
		Assert.assertEquals(3, LevenshteinDistanceAlgorithm.getDistanceEfficiently("abc", "0b1d"));
	}
	
	/**
	 * Verifica se o resultado da distância entre as palavras X e Y é igual a distância entre as palavras Y e X.
	 */
	@Test
	public void testSimetry(){
		Assert.assertEquals(
				LevenshteinDistanceAlgorithm.getDistance("banana", "abacate"), 
				LevenshteinDistanceAlgorithm.getDistance("abacate", "banana")
			);
		
		Assert.assertEquals(
				LevenshteinDistanceAlgorithm.getDistanceEfficiently("banana", "abacate"), 
				LevenshteinDistanceAlgorithm.getDistanceEfficiently("abacate", "banana")
			);
	}
	
	/**
	 * Nunca pode ocorrer de uma ou ambas as palavras serem valores inválidos, ou seja NULL.
	 */
	@Test
	public void testNullParameter(){
		try{
			LevenshteinDistanceAlgorithm.getDistanceEfficiently(null, "abacate");
			Assert.fail("O método getDistanceEfficiently() aceitou o primeiro parâmetro como NULL.");
		}catch(IllegalArgumentException e){ }
		
		try{
			LevenshteinDistanceAlgorithm.getDistanceEfficiently("banana", null);
			Assert.fail("O método getDistanceEfficiently() aceitou o segundo parâmetro como NULL.");
		}catch(IllegalArgumentException e){ }
		
		try{
			LevenshteinDistanceAlgorithm.getDistance(null, "abacate");
			Assert.fail("O método getDistance() aceitou o primeiro parâmetro como NULL.");
		}catch(IllegalArgumentException e){ }
		
		try{
			LevenshteinDistanceAlgorithm.getDistance("banana", null);
			Assert.fail("O método getDistance() aceitou o primeiro parâmetro como NULL.");
		}catch(IllegalArgumentException e){ }
		
		try{
			LevenshteinDistanceAlgorithm.getDistance(null, null);
			Assert.fail("O método getDistance() aceitou ambos os parâmetros como NULL.");
		}catch(IllegalArgumentException e){ }
		
		try{
			LevenshteinDistanceAlgorithm.getDistanceEfficiently(null, null);
			Assert.fail("O método getDistanceEfficiently() aceitou ambos os parâmetros como NULL.");
		}catch(IllegalArgumentException e){ }
		
	}
	
	/**
	 * Caso uma das palavras passadas seja uma String válida, mas de tamanho 0, a distância tem que ser o tamanho da outra palavra
	 */
	@Test
	public void testLenghtZeroParameter(){
		Assert.assertEquals("banana".length(), LevenshteinDistanceAlgorithm.getDistanceEfficiently("", "banana"));
		Assert.assertEquals("banana".length(), LevenshteinDistanceAlgorithm.getDistanceEfficiently("banana", ""));
		Assert.assertEquals("banana".length(), LevenshteinDistanceAlgorithm.getDistance("", "banana"));
		Assert.assertEquals("banana".length(), LevenshteinDistanceAlgorithm.getDistance("banana", ""));
	}

}
