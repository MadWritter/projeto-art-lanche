<h1><b>Projeto Art Lanche</b></h1>

Feito com Java 17 e Maven, JavaFX 21, PostgreSQL 16


Como buildar o Projeto:

Requisitos: Java 17 ou superior, Maven e PostgreSQL

Windows:
1) Execute como Administrador o arquivo "configure.bat" no windows, em seguida, preencha os dados de conexão com o banco.
2) O projeto irá iniciar automaticamente após preencher os dados.
2) Caso tenha preenchido os dados incorretamente, execute o "configure.bat" siga os passos novamente.
4) O Projeto ficará na pasta build, basta executar o jar com "java -jar app-1.0.jar"

Linux:
1) Dê permissão ao arquivo "configure.sh" com "sudo chmod +rwx configure.sh".
2) Execute com "./configure.sh" e informe os dados de conexão com o banco.
3) Após ter preenchido corretamente, o projeto será iniciado automaticamente, caso erre os dados de conexão, basta executar o "configure.sh" novamente e repetir o processo.
4) O Projeto ficará na pasta build, basta executar o jar com "java -jar app-1.0.jar"