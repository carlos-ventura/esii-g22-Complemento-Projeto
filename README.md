# ESII-G22-2020

Andre Henriques 82203 adsaj@iscte-iul.pt -> Complemento 1

Miguel Maior 82229 pmmsm1@iscte-iul.pt -> Complemento 2

Carlos Ventura 82488 cdpva@iscte-iul.pt -> Complemento 3

Guilherme Rodrigues 82487 girse@iscte-iul.pt -> Complemento 4

Rodrigo Vidigal 82878 rmmvs1@iscte.pt -> Complemento 5

Miguel Figueiredo 82282 mrcfo1@iscte-iul.pt -> Complemento 6


# A completude dos complementos esta descrito nos readme(numero do complemento)

# Erros encontrados
O site foi gerado a partir do Toolbox, ao tentar correr o site wordpress no Docker Hub, esta aparece muito lento, muito desconfigurado e certas paginas nao funcionam

Ao tentar correr o site no Toolbox de outra maquina que nao a que criou ocorre um erro de base de dados “Error Establishing a Database Connection”

# Instrucoes de Utilizacao

Ao abrir a pen, encontrarao duas pastas: Execucao(zip) e Codigo_Fonte, como dito no nome uma pasta tem o codigo fonte (zip) e a outra pasta tem o necessario para a execucacao do site

Dentro da pasta Execucao(zip) havera uma pasta chamada esii-g22 e dentro desta pasta estara disponivel 3 pastas: html, cgi-bin e mysql; e 3 ficheiros chamados docker-compose.yml, mysql.tar e wordpressjava.tar 

Aconselha-se ao utilizador para criar uma pasta no desktop e colocar, estas 3 pastas e 3 ficheiros referidss anteriormente, dentro

Ou seja dentro da nova pasta criada deverao ter 3 pastas e 3 ficheiros

Aceder a linha de comandos e colocar-se no path da pasta, apos isto escrever o codigo "docker load -i mysql.tar", depois escrever o codigo "docker load -i wordpressjava.tar" e por fim escrever " docker-compose up -d "

Caso esteja a aceder pelo Toolbox, devera escrever no browser: 192.168.99.100

Caso esteja a aceder pelo DockerHub, devera escrever no browser: localhost:80, para alem disto devera ir dentro da pasta cgi-bin
e editar o ficheiro chamado covid-sci-discoveries.sh, e onde diz "192.168.99.100" substituir por "localhost"



# Nota

Foi testado a criacao de um site teste a partir do dockerhub e partilhado com outras maquinas e funcinou sem problemas nas maquinas que tinham docker hub

