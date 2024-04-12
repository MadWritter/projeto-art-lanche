<h1><b>Projeto Art Lanche</b></h1>

Feito com Java 17 e Maven, JavaFX 21, PostgreSQL 16


Como buildar o Projeto:

Requisitos: Java 17 ou superior, Maven e PostgreSQL

Windows:
1) Execute como Administrador o arquivo "configure.bat" no windows, no final, ele vai pedir os dados de conexão com o banco de dados
2) Execute o jar na raiz do projeto

Linux:
1) Execute mvn clean package no diretório do pom e copie a pasta libs e o jar para a pasta raiz do repositório
2) Crie um arquivo chamado "connection.properties" em "config/database" com as configs de conexão:
url=?
usr=?
pwd=?
3) Execute o jar na raiz do projeto