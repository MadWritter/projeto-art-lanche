#!/bin/bash

script_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

echo "Limpando o Projeto..."
cd "$script_dir" || exit
rm -rf libs
rm -f app-*

cd app || exit
mvn clean
sleep 3

echo "Compilando o projeto..."
mvn package
sleep 3

echo "Reorganizando os arquivos do projeto..."
mv -f target/app-1.0.jar ../
mv -f target/libs ../

read -p "Informe a url jdbc: " url
read -p "Informe o login de usuário: " usr
read -p "Informe a senha do usuário: " pwd

# Constrói o caminho do arquivo usando o diretório do script
arquivo_banco="$script_dir/config/database/connection.properties"

# Cria o diretório se não existir
mkdir -p "$(dirname "$arquivo_banco")"

if [ -f "$arquivo_banco" ]; then
    rm "$arquivo_banco"
fi

echo "url=$url" > "$arquivo_banco"
echo "usr=$usr" >> "$arquivo_banco"
echo "pwd=$pwd" >> "$arquivo_banco"

echo "Configurado com sucesso"
read -p "Pressione Enter para continuar..."
