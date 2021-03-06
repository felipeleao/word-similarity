# Word-Similarity

Exemplo de implementação em Java de um serviço RESTful para cálculo da similaridade entre palavras.

O serviço foi criado com o auxílio do [framework Spring](https://spring.io/) e o cálculo de similaridade realizado com base no [algoritmo de distância de Levenshtein](https://pt.wikipedia.org/wiki/Distância_Levenshtein)

## Funcionalidades disponíveis

- Receber e armazenar uma nova palavra (em memória).
- Recuperar a lista de palavras armazenadas.
- Recuperar uma lista de palavras similares à uma palavra informada como _keyword_.

## Instruções

A compilação deve ser realizada com auxílio da ferramenta Maven (versão 3.3.9+) e Java 8. Uma vez empacotada a aplicação não dependerá de um servidor de aplicação ou container web para que possa ser executada. Quando executada por linha de comando a aplicação levantará o serviço RESTful na porta 8080.

__Atenção__: É possível alterar a porta do serviço editando o arquivo de propriedades em `src/main/resources/application.properties`.

### Compilação e uso (Maven)

1. Instalar maven.
   ```bash 
   # Macintosh (homebew)
   $ brew install maven
   
   # Debian based SO (e.g. Ubuntu)
   $ sudo apt-get install maven
   
   # RHEL based SO (e.g. CentOS, Red Hat, Fedora)
   $ yum install maven
   ```    
    Para instalar em ambientes Windows ou caso haja problemas em ambientes UNIX, consulte a [documentação oficial](https://maven.apache.org/install.html).

2. Compilar e empacotar a aplicação (_Build_)
   ```bash
   # Compilar o código gerando um JAR executável
   $ mvn clean package
   ```
   O arquivo JAR gerado poderá se encontrado dentro do diretório `target/`

    __Observação:__ Ao empacotar a aplicação os testes unitários serão executados automaticamente. Caso necessário é possível empacotar sem executar os testes, adicionando a flag `-Dmaven.test.skip=true`

3. Levantar o serviço RESTful
    ```bash
    # Levantar o serviço
    $ java -jar target/word-similarity-0.0.1-SNAPSHOT.jar
    ```
    A última linha de log informará que o serviço está ativo. O terminal ficará preso enquanto o serviço estiver em execução. Para encerrar o serviço, basta dar o comando `Ctrl + C`.
4. Acessar o serviço via HTTP

    O serviço deve ser acessado através de requisições HTTP que podem ser realizadas via navegador ou pelo comando `curl`. Abaixo seguem alguns exemplos de utilização das funcionalidades disponibilizadas junto aos retornos esperados:

    > Enviando uma palavra para armazenar internamente no serviço
    ```bash
    # Adicionando a palavra "manhã"
    $ curl http://localhost:8080/rest/add/manhã
    >> true
    ```
    ```bash
    # Adicionando a palavra "tarde"
    $ curl http://localhost:8080/rest/add/tarde
    >> true
    ```
    ```bash
    # Adicionando a palavra "noite"
    $ curl http://localhost:8080/rest/add/noite
    >> true
    ```
    ```bash
    # Adicionando a palavra "abacate"
    $ curl http://localhost:8080/rest/add/abacate
    >> true
    ```

    > Recuperando a lista com todas as palavras armazenadas
    ```bash
    # Recuperando tudo
    curl http://localhost:8080/rest/listAll
    >> ["abacate","tarde","manhã","noite"]
    ```

    > Recuperando a lista de palavras similares a uma dada keyword
    ```bash
    # Recuperando palavras similares a "boate"
    # (usando threshold default)
    curl http://localhost:8080/rest/listSimilar/boate
    >> ["abacate","noite"]
    ```
    ```bash
    # Recuperando palavras similares a "banana"
    # (sem especificar o threshold)
    curl http://localhost:8080/rest/listSimilar/banana
    >> []
    # (especificando um threshold)
    curl http://localhost:8080/rest/listSimilar/banana?threshhold=4
    >> ["abacate","manhã"]
    ```

**Observação:** Antes de inicializar o serviço verifique se a porta escolhida para disponibilizá-lo (8080 por padrão) está livre, ou seja, que não há outros serviços como o Apache (_httpd_) utilizando-a, e que o acesso à porta não está sendo bloqueado por um firewall nativo do sistema operacional.

## Autoria

Projeto criado e originalmente disponibilizado por Felipe Leão ([@felipeleao](http://github.com/felipeleao))
