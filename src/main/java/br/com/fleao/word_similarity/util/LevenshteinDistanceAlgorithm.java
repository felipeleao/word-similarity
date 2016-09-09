package br.com.fleao.word_similarity.util;

/**
 * <p>
 * Classe com implementação do algoritmo para cálculo de distância de Levenshtein. Este algoritmo 
 * calcula a quantidade de modificações (inserção, substituição e remoção de caracteres) que devem 
 * ser aplicadas a uma palavra para que ela se transforme na outra palavra.
 * </p>
 * <p>
 * A classe disponibiliza duas formas de cálculo da distância:
 * </p>
 * <ol>
 *   <li>Cálculo padrão, armazenando todos os valores calculados durante a execução do método.</li>
 *   <li>Cálculo com uso eficiente de memória, armazenando somente as duas últimas linhas da matriz de distância.</li>
 * </ol>
 * 
 * @author felipe
 *
 */
public final class LevenshteinDistanceAlgorithm {

	/**
	 * Declaração do construtor como provado para imepdir a instanciação da classe
	 */
	private LevenshteinDistanceAlgorithm(){}
	
	/**
	 * <p>
	 * Calcula a distância de levenshtein entre duas palavras. O Algoritmo foi implementado em 
	 * sua forma padrão, não otimizada, portanto sua complexidade é de O(mn). A distância entre 
	 * as duas palavras informadas consiste na quantidade de operações que de inserção, substituição
	 * e remoção de caracteres que precisam ser executadas para transformar uma string na outra.
	 * </p>
	 * <p>
	 * Para realizar o cálculo de distância utilizando a memória de modo mais eficiente, consulte o
	 * método {@link #getDistanceEfficiently(String left, String right)}.
	 * </p>
	 * 
	 * @param left primeira palavra
	 * @param right segunda palavra
	 * @return distância entre as palavras
	 */
	public final static int getDistance(String left, String right) throws IllegalArgumentException{
		// Validar argumentos
		if(left == null || right == null){
			throw new IllegalArgumentException("Os parâmetros fornecidos não podem ser nulos.");
		}
		
		// Se o tamanho de uma palavra for zero, a distância equivale ao tamanho da outra palavra
		if (left.length() == 0)
			return right.length();
		if (right.length() == 0)
			return left.length();
		
		// Criar matriz para armazenar todos os valores calculados
		int[][] distance = new int[left.length() + 1][right.length() + 1];
		
		// Inserir valores base (primeira linha e primeira coluna)
		for (int i = 0; i <= left.length(); i++)
			distance[i][0] = i;
		for (int j = 1; j <= right.length(); j++)
			distance[0][j] = j;

		// Calcular os valores da matriz
		for (int i = 1; i <= left.length(); i++)
			for (int j = 1; j <= right.length(); j++)
				/*
				 * Encontra o mínimo entre 3 diferentes valores. O menor valor é colocado no matriz: 
				 *    valor 1) valor na coluna anterior ao valor sendo calculado na mesma linha da matriz +1
				 *    valor 2) valor da mesma coluna do valor sendo calculado, porém na linha anterior +1 
				 *    valor 3) valor na coluna anterior ao valor sendo calculado e na linha anterior ao valor sendo 
				 *             calculado +0 ou +1, dependendo se o caractere da linha é igual ao caractere da coluna 
				 *             ou não, respectivamente.
				 */
				distance[i][j] = minimum( //
						distance[i - 1][j] + 1, //
						distance[i][j - 1] + 1, //
						distance[i - 1][j - 1] + ((left.charAt(i - 1) == right.charAt(j - 1)) ? 0 : 1));

		return distance[left.length()][right.length()];

	}
	
	/**
	 * <p>
	 * Método para calcular a distância de Levenshtein entre duas Strings otimizando o uso de memória.
	 * </p>
	 * <p>
	 * A distância de Levenshtein consiste na quantidade de operações de inserção, substituição e remoção que precisam se
	 * aplicadas a uma palavra para que ela seja transformada em uma outra palavra. Quanto menor o valor resultante, diz-se que
	 * menor é a distância entre as duas palavras, ou seja, menores distâncias indicam palavras mais similares.
	 * </p>
	 * <p>
	 * Ao invés de armazenar uma matriz de tamanho "M*N", aonde M e N são respectivamente os tamanhos das strings submetidas, o
	 * método armazena apenas dois arrays unidimensionais de tamanho M. O algoritmo otimiza o uso de memória pois armazena somente
	 * o array atualmente sendo calculado e o array calculado na iteração anterior. Como os valores vão sendo sobreescritos, não
	 * há redução de tempo de alocação de espaço em memória.
	 * </p>
	 * 
	 * @param left  primeira palavra
	 * @param right segunda palavra
	 * 
	 * @return Distância de Levenshtein entre as palavras
	 */
	public static int getDistanceEfficiently(String left, String right) {
		// Validação dos parâmetros
		if (left == null || right == null)
			throw new IllegalArgumentException("Os parâmetros fornecidos não podem ser nulos.");

		// Se os parâmetros forem válidos, mas uma das strings for vazia, a distância é o tamanho da outra string
		if (left.length() == 0)
			return right.length();
		if (right.length() == 0)
			return left.length();

		// Criação dos arrays para comportar os valores calculados.
		int[] previousRow = new int[left.length() + 1];
		int[] currentRow = new int[left.length() + 1];

		// Inicialização do primeiro array --> [0,1,2,3,4,5,...]
		for (int i = 0; i <= left.length(); i++) {
			previousRow[i] = i;
		}

		// Loop para calcular os valores subsequentes do array com base no array calculado anteriormente.
		for (int j = 1; j <= right.length(); j++) {
			currentRow[0] = j;

			for (int k = 1; k <= left.length(); k++) {
				/*
				 * Encontra o mínimo entre 3 diferentes valores. O menor valor é colocado no array na posição "k": 
				 *    valor 1) valor anterior do array sendo calculado +1 
				 *    valor 2) valor de mesma posição do array previamente calculado +1 
				 *    valor 3) valor "k-1" do array previamente calculado adicionado de "0" caso o caractere k-1 
				 *             da string "left" seja igual ao caractere j-1 da string "right". Caso sejam diferentes 
				 *             adiciona-se "1".
				 */
				currentRow[k] = minimum( //
						// caso 1
						currentRow[k - 1] + 1, //
						// caso 2
						previousRow[k] + 1, //
						// caso 3
						previousRow[k - 1] + ((left.charAt(k - 1) == right.charAt(j - 1)) ? 0 : 1) //
				);
			}
			
			// Sobreescrever o array mais antigo com os dados do array recém calculado
			previousRow = currentRow.clone();
		}

		return currentRow[left.length()];
	}
	
	/**
	 * Método auxiliar para determinar o menor valor entre três valores informados.
	 * 
	 * @param a primeiro valor
	 * @param b segundo valor
	 * @param c terceiro valor
	 * @return o menor entre os três valores
	 */
	private static int minimum(int a, int b, int c) {
		return Math.min(Math.min(a, b), c);
	}
	
}
