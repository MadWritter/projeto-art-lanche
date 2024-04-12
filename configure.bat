@echo off
echo Limpando o Projeto...
cd %~dp0
RD /S /Q libs
del /Q /F app-*
DEL /Q /F config\database\connection.properties
cd %~dp0\app
call mvn clean
timeout /t 3

echo Compilando o projeto...
call mvn package
timeout /t 3

echo Reorganizando os arquivos do projeto...
move /y target\app-1.0.jar ..\
move /y target\libs ..\
cd %~dp0

set /p url="Informe a url jdbc: "
set /p usr="Informe o login de usuário: "
set /p pwd="Informe a senha do usuário: "

echo url=%url%>>config\database\connection.properties
echo usr=%usr%>>config\database\connection.properties
echo pwd=%pwd%>>config\database\connection.properties

echo Configurado com sucesso
pause
