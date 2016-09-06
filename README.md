# Word-Similarity

Exemplo de implementação em Java de um serviço RESTful para cálculo da similaridade entre palavras.

O serviço foi criado com o auxílio do [framework Spring](https://spring.io/) e o cálculo de similaridade realizado com base no [algoritmo de distância de Levenshtein](https://pt.wikipedia.org/wiki/Distância_Levenshtein)

## Funcionalidades disponíveis

- Receber e armazenar uma nova palavra (em memória).
- Recuperar a lista de palavras armazenadas.
- Recuperar uma lista de palavras similares à uma palavra informada como _keyword_.

## Instruções de compilação e uso

A compilação pode ser realizada com auxílio da ferramenta Maven (versão 3.3.9+) ou com a IDE Eclipse, através da funcionalidade de `Export > Runnable JAR File` (Escolher a classe `Runner.java` para inicializar a aplicação).

Uma vez gerado o JAR, basta executá-lo por linha de comando para que o serviço seja leventado e possa ser acessado via requisições HTTP.

### Instruções de compilação e uso (Maven)

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

- Realizar o build da aplicação
- Inicializar o serviço RESTful
- Acessar o serviço via HTTP

**Observação:** Antes de inicializar o serviço verifique se a porta 8080 está livre, ou seja, que não há outros serviços como o Apache (_httpd_) utilizando-a, e que o acesso à porta não está sendo bloqueado por um firewall nativo do sistema operacional.


## Autoria

Projeto criado e originalmente disponibilizado por Felipe Leão ([@felipeleao](http://github.com/felipeleao))
