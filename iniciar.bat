@echo off
ECHO =======================================================
ECHO       INICIANDO AMBIENTE DE DESENVOLVIMENTO PETDOC
ECHO =======================================================

ECHO.
ECHO --- [PASSO 0 de 4] Verificando configuracao Java do sistema...
ECHO Usando JAVA_HOME encontrado em: %JAVA_HOME%
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