#!/bin/bash
echo "======================================================="
echo "      INICIANDO AMBIENTE DE DESENVOLVIMENTO PETDOC"
echo "======================================================="

echo ""
echo "--- [PASSO 1 de 3] Verificando se o Docker está rodando..."
if (! docker stats --no-stream ); then
  echo ""
  echo "!-- ERRO: Docker Desktop não parece estar em execução. --!"
  echo "Por favor, inicie o Docker e tente novamente."
  exit 1
fi
echo "Docker OK!"

echo ""
echo "--- [PASSO 2 de 3] Subindo o container do banco de dados (PostgreSQL)..."
docker-compose up -d
echo "Container do banco de dados iniciado!"

echo ""
echo "--- [PASSO 3 de 3] Iniciando a aplicação Spring Boot..."
echo "(Isso pode levar alguns minutos na primeira vez)"
echo ""
cd backend
./mvnw spring-boot:run

echo ""
echo "--- Script finalizado ---"