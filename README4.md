# Complemento 4
82487 Guilherme Rodrigues

Requisito 4

Neste requisito todos os objetivos foram alcancados:

Aceder ao ficheiro "covid19spreading.rdf" no Github.

Obter todas as versões desse ficheiro com Tags associadas

Criar tabela com 5 colunas e os repetivos conteudos

O programa está preparado para receber argumentos, onde o primeiro será o link do GitHub e o 2º o path no Github para o ficheiro a analisar.
Este extra feito está implementado mas está em comentário.

Erros que ocurreram e que foram corrigidos/substituidos:
Tentativa de fazer fetch do GitHub causava longa demora e por vezes crash no eclipse. Optou-se por apagar o Repositorio Local sempre que se executava o programa.

Na pasta estara presente o jar

Para executar a aplicacao tera de se executar um script:

#!/bin/bash
chmod -R 777 Git
rm -rf Git
java -jar Complement4.jar

Em cima esta presente um exemplo de um script


Para executar a aplicacao no site sera preciso um link do tipo http://localhost/cgi-bin/Complement4.sh

Sendo Complement4.sh o nome do script

Coverage:

Type Counters 100%

Method Counters 78.6% 
