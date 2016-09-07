package br.com.fleao.word_similarity.services;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.stereotype.Service;

/**
 * Serviço spring para realizar as ações relacionadas ao armazenamento e recuperação de palavras armazenadas. Entre
 * as funções disponíveis se encontra a de invocação.
 * 
 * Nesta versão da aplicação, por não haver a necessidade de persistência das palavras recebidas pela aplicação, todas 
 * as palavras adicionadas via invocação do serviço REST são armazenadas em memória e disponíveis apenas em tempo de 
 * execução.
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
		lock.writeLock().lock();
		try{
			bagOfWords.add(word);
		}finally{
			lock.writeLock().unlock();
		}
	}
}
