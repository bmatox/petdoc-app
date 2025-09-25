@echo off
ECHO =======================================================
ECHO       INICIANDO AMBIENTE DE DESENVOLVIMENTO PETDOC
ECHO =======================================================

ECHO.
ECHO --- [PASSO 0 de 4] Configurando ambiente Java para esta sessao...
set "PETDOC_JAVA_HOME=C:\Users\Bruno Matos\.jdks\openjdk-21.0.2"
set "JAVA_HOME=%PETDOC_JAVA_HOME%"
set "PATH=%PETDOC_JAVA_HOME%\bin;%PATH%"
ECHO Java 21 configurado temporariamente para este script.
java -version

ECHO.
ECHO --- [PASSO 1 de 4] Verificando se o Docker esta rodando...
docker ps > nul 2>&1
if %errorlevel% neq 0 (
    ECHO.
    ECHO !-- ERRO: Docker Desktop nao parece estar em execucao. --!
    ECHO Por favor, inicie o Docker e tente novamente.
    pause
    exit /b
)

ECHO Docker OK!

ECHO.
ECHO --- [PASSO 2 de 4] Subindo o container do banco de dados (PostgreSQL)...
docker-compose up -d
ECHO Container do banco de dados iniciado!

ECHO.
ECHO --- [PASSO 3 de 4] Iniciando a aplicacao Spring Boot...
ECHO (Isso pode levar alguns minutos na primeira vez)
ECHO.
call mvnw spring-boot:run

ECHO.
ECHO --- Script finalizado ---
pause