package br.com.fleao.word_similarity.services;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.stereotype.Service;

import br.com.fleao.word_similarity.util.LevenshteinDistanceAlgorithm;

/**
 * <p>
 * Serviço spring para realizar as ações relacionadas ao armazenamento e recuperação de palavras armazenadas. Entre
 * as funções disponíveis se encontra a de invocação.
 * </p>
 * <p>
 * Nesta versão da aplicação, por não haver a necessidade de persistência das palavras recebidas pela aplicação, todas 
 * as palavras adicionadas via invocação do serviço REST são armazenadas em memória e disponíveis apenas em tempo de 
 * execução.
 * </p>
 * 
 * @author felipe
 *
 */
@Service
public class WordService {
	
	// Estrutura de dados para armazenar as palavras em tempo de execução. Por ser um "Set", não há duplicação.
	private final Set<String> bagOfWords = new HashSet<String>();
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	/**
	 * Retorna o conjunto com todas as palavras recebidas e armazenadas pela aplicação desde sua inicialização.
	 * 
	 * @return conjunto com todas as palavras armazenadas
	 */
	public Set<String> getAllStoredWords(){
		lock.readLock().lock();
		try{
			return bagOfWords;
		}finally{
			lock.readLock().unlock();
		}
	}
	
	/**
	 * Adiciona uma palavra ao conjunto de palavras armazenadas em memória. Caso a palavra informada já tenha 
	 * sido armazenada anteriormente, ela não será duplicada.
	 * 
	 * @param word palavra a ser armazenada
	 */
	public void storeWord(String word){
		if(word == null)
			throw new IllegalArgumentException("O parâmetro informado não pode ser nulo.");
		
		lock.writeLock().lock();
		try{
			bagOfWords.add(word);
		}finally{
			lock.writeLock().unlock();
		}
	}

	/**
	 * Retorna um conjunto de palavras cuja distância de similaridade de acordo com o algoritmo de Distância de 
	 * Levenshtein é igual ou inferior ao threshold informado. O conjunto de palavras retornado é um subconjunto 
	 * das palavras atualmente armazenadas pela aplicação. Quanto menor o valor calculado da distância, mais 
	 * similares são duas palavras.
	 * 
	 * @param keyword palavra cujos similares deseja-se encontrar.
	 * @param threshold distância máxima para considerar duas palavras como similares
	 * @return conjunto de palavras com distância para a keyword igual ou inferior ao threshold
	 * @throws IllegalArgumentException caso algum dos parâmetros informados seja inválido
	 */
	public Set<String> listSimilarStoredWords(String keyword, int threshold) throws IllegalArgumentException{
		// Validação dos parâmetros
		if(threshold < 0){
			throw new IllegalArgumentException("O threshold informado deve ser positivo.");
		}else if(keyword == null){
			throw new IllegalArgumentException("A keyword informada é inválida.");
		}
		
		// Cálculo das palavras similares compatíveis com o threshold
		Set<String> similarWords = new HashSet<String>();
		for(String storedWord : getAllStoredWords()){
			if(LevenshteinDistanceAlgorithm.getDistanceEfficiently(keyword, storedWord) <= threshold){
				similarWords.add(storedWord);
			}
		}

		return similarWords;
	}
	
	
}
