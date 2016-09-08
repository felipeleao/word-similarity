package br.com.fleao.word_similarity.util;

/**
 * Classe com implementação do algoritmo para cálculo de distância de Levenshtein. Este algoritmo 
 * calcula a quantidade de modificações (inserção, substituição e remoção de caracteres) que devem 
 * ser aplicadas a uma palavra para que ela se transforme na outra palavra.
 * @author felipe
 *
 */
public final class LevenshteinDistanceAlgorithm {

	/**
	 * Declaração do construtor como provado para imepdir a instanciação da classe
	 */
	private LevenshteinDistanceAlgorithm(){}
	
	/**
	 * Calcula a distância de levenshtein entre duas palavras. O Algoritmo foi implementado em 
	 * sua forma padrão, não otimizada, portanto sua complexidade é de O(mn). A distância entre 
	 * as duas palavras informadas consiste na quantidade de operações que de inserção, substituição
	 * e remoção de caracteres que precisam ser executadas para transformar uma string na outra.
	 * 
	 * @param left primeira palavra
	 * @param right segunda palavra
	 * @return distância entre as palavras
	 */
	public final static int calculate(String left, String right) throws IllegalArgumentException{
		// Validar argumentos
		if(left == null || right == null){
			throw new IllegalArgumentException("Os parâmetros não podem ser nulos.");
		}
		
		// Se o tamanho de uma palavra for zero, a distância equivale ao tamanho da outra palavra
		if(left.length() == 0){
			return right.length();
		}else if(right.length() == 0){
			return left.length();
		}
		
		//TODO Completar implementação 
		return 0;
	}
}
