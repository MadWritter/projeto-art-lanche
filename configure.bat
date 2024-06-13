@echo off
echo ---------------------------
echo Limpando o Projeto...
cd %~dp0
IF EXIST build RD /S /Q build
IF EXIST config\database\connection.properties DEL /Q /F config\database\connection.properties
echo ---------------------------
echo Configurando o banco

set /p url="Informe a url jdbc: "
set /p usr="Informe o usuario: "
set /p pwd="Informe a senha: "

echo url=%url%>>config\database\connection.properties
echo usr=%usr%>>config\database\connection.properties
echo pwd=%pwd%>>config\database\connection.properties
echo ---------------------------
echo Compilando o projeto...
cd %~dp0\app
call mvn package
if errorlevel 1 (
    echo Erro ao compilar o projeto
    pause
    exit /b 1
)
timeout /t 3
echo ---------------------------
echo Reorganizando os arquivos do projeto...
mkdir ..\build
move target\app-1.0.jar ..\build
move target\libs ..\build
cd %~dp0
xcopy /E /I config build\config
xcopy /E /I resources build\resources
echo ---------------------------
echo Configurado com sucesso
echo ---------------------------
echo Executando o Projeto
echo ---------------------------
cd %~dp0\build
call java -jar app-1.0.jar
pause
