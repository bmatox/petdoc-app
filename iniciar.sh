#!/bin/bash
echo "======================================================="
echo "      INICIANDO AMBIENTE DE DESENVOLVIMENTO PETDOC"
echo "======================================================="

echo ""
echo "--- [PASSO 0 de 4] Verificando a versao do Java..."
if type -p java; then
    _java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
    _java="$JAVA_HOME/bin/java"
else
    echo "!-- ERRO: Java nao encontrado no seu sistema. --!"
    exit 1
fi

version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
if [[ "$version" -lt "21" ]]; then
    echo "!-- ERRO: Versao do Java inadequada. Encontrado: $version. Requerido: 21 ou superior. --!"
    exit 1
fi
echo "Java OK (versao $version)."

echo ""
echo "--- [PASSO 1 de 4] Verificando se o Docker esta rodando..."
if (! docker stats --no-stream ); then
  echo ""
  echo "!-- ERRO: Docker Desktop nao parece estar em execucao. --!"
  echo "Por favor, inicie o Docker e tente novamente."
  exit 1
fi
echo "Docker OK!"

echo ""
echo "--- [PASSO 2 de 4] Subindo o container do banco de dados (PostgreSQL)..."
docker-compose up -d
echo "Container do banco de dados iniciado!"

echo ""
echo "--- [PASSO 3 de 4] Iniciando a aplicacao Spring Boot..."
echo "(Isso pode levar alguns minutos na primeira vez)"
echo ""
./mvnw spring-boot:run

echo ""
echo "--- Script finalizado ---"