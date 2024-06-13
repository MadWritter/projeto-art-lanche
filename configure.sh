echo ---------------------------
echo Limpando o projeto
echo ---------------------------
rm -rf build
rm config/database/connection.properties
echo ---------------------------
echo Configurando o banco
echo ---------------------------
echo Informe a url jdbc:
read url
echo Informe o usuário:
read usr
echo Informe a senha:
read pwd
echo ---------------------------
echo Criando arquivo de conexão
echo ---------------------------
touch config/database/connection.properties
echo url=$url>>config/database/connection.properties
echo usr=$usr>>config/database/connection.properties
echo pwd=$pwd>>config/database/connection.properties
echo ---------------------------
echo Compilando o Projeto...
echo ---------------------------
cd app
mvn clean package
echo ---------------------------
echo Organizando o projeto
echo ---------------------------
mkdir ../build
mv target/app-1.0.jar ../build
mv target/libs ../build
cd ..
cp -r config build
cp -r resources build
cd build
echo ---------------------------
echo Executando o projeto...
echo ---------------------------
java -jar app-1.0.jar
