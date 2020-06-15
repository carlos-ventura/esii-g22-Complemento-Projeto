82488 Carlos Ventura https://github.com/cdpva-iscteiul/esii-g22-Requirement3 

Requisito 3

Neste requisito todos os objetivos foram alcancados:

Aceder ao sistema de ficheiros para obter os pdf's

Obter a metadata dos ficheiros

Criar tabela com 4 colunas

Criar um hyperlink de modo a redirecionar para a pagina do pdf especifico

Foi implementado uma forma de acelerar em muito o programa!

Na primeira execucao, a metadata foi gerada atraves do "metodo lento"

Apos esta execucao a metadata dos ficheiros existentes sera guardada num ficheiro texto

Quando executado outra vez a aplicacao ira obter a metadata atraves do ficheiro txt correndo o programa assim MUITO MAIS RAPIDO

Ao adicionar novos pdf's estes terao a sua metada gerada pelo "metodo lento", e os pdfs existentes no ficheiro txt da forma "rapida"

Na pasta estara presente o jar

Para executar a aplicacao tera de se executar um script:

#!/bin/bash
java -jar covid-sci-discoveries.jar /var/www/html/wp-content/uploads/simple-file-list http://localhost/wp-content/uploads/simple-file-list

Em cima esta presente um exemplo de um script

O primeiro argumento representa o path para onde os pdf's estao presentes no container

Exemplo: /var/www/html/wp-content/uploads/simple-file-list Relembrar de nao colocar "/" no final

O segundo argumento corresponde ao path online do ficheiro pdf, neste caso da PASTA!

Exemplo de localizacao de 1 FICHEIRO: http://localhost/wp-content/uploads/simple-file-list/pdf1.pdf

Neste caso o argumento seria http://localhost/wp-content/uploads/simple-file-list, relembrar de nao colocar "/" no final

Para executar a aplicacao no site sera preciso um link do tipo http://localhost/cgi-bin/covid-sci-discoveries.sh

Sendo covid-sci-discoveries.sh o nome do script

Coverage:

Type Counters 100%

Method Counters 75% 
