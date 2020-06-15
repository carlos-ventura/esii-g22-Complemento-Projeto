Rodrigo Vidigal da Silva nº 82878 repositorio:https://github.com/VIdigalSilva/covid-queryC5

Requisito 5

No meu projeto fiz dum metodo em que ia adicionando os campos de pesquisa no XPath a uma string e no fim era feita a query de acordo com a string.
O que não consegui implementar foram o Not por não perceber como o fazer. De resto tinha em intenção utilizar java script para irem aparecendo novos campos mas não me foi possivel devido a falta tempo.
Por isso as partes que foram parcialmente implementadas foram mais a parte de extender o formulario para abrangir mais campos, que não o fiz por não estar no enunciado por isso decidi assim poupar tempo dado que foi complicado este complemento.

Para executar esta aplicação basta gerar uma aplicação Java executavel ('cgi-java.jar') que possa ser executada a partir de uma pagina/formulario web.

Execução:
Para executar este complemento tera de ter o jar que esta na pasta e dois scripts shell deste tipo:
1º:
#!/bin/bash
java -jar cgi-java.jar GET
2º
#!/bin/bash
java -jar cgi-java.jar POST

Para execução do complemento teremos de executar o primeiro shell script sendo que o segundo sera executado recursivamente.
Desta forma para executar esse script teremos que adicionar um botão ao wordpress com /cgi-bin/comp3.sh
Isto irá executar as querys e assim estará efetuado a execução do complemento.

