# Pet Doc - Sua Carteira de Vacinação PET

[![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)](https://github.com/bmatox/petdoc-app)

O Pet Doc é uma plataforma digital para centralizar e gerenciar o histórico de vacinação de animais de estimação, substituindo a caderneta de papel e automatizando lembretes de doses futuras.

## ✨ Funcionalidades (MVP)

-   [ ] **Cadastro de Tutores e Pets:** Gerenciamento centralizado das informações dos animais.
-   [ ] **Carteira de Vacinação Digital:** Registro completo do histórico de vacinas.
-   [ ] **Lembretes Automáticos:** Notificações por e-mail sobre as próximas doses.
-   [ ] **Autenticação Segura:** Acesso protegido por login e senha com controle de sessão.

## 🏛️ Arquitetura do Projeto

A aplicação é construída sobre uma **Arquitetura Monolítica com Renderização no Servidor (Server-Side Rendering)**, utilizando o padrão **MVC (Model-View-Controller)**.

O Spring Boot é responsável por todo o fluxo: ele recebe a requisição do navegador, processa a lógica de negócio, acessa o banco de dados e, por fim, utiliza o Thymeleaf como *template engine* para gerar e enviar uma página HTML completa de volta para o usuário.

```mermaid
graph TD
    A["Navegador do Usuário"] -- Requisição HTTP --> B{"Spring Boot MVC"};
    B -- Chama --> C["Controller"];
    C -- Usa --> D["Service (Lógica de Negócio)"];
    D -- Acessa --> E["Repository"];
    E -- Interage com --> F[("Banco de Dados")];
    F -- Retorna Dados --> E;
    E -- Retorna Dados --> D;
    D -- Retorna Dados --> C;
    C -- Adiciona dados ao Model --> G["View - Thymeleaf Template"];
    G -- Renderiza --> H["Página HTML Completa"];
    B -- Envia HTML --> A;
```

### Camadas da Aplicação

A aplicação é organizada em uma **Arquitetura em Camadas** para garantir a separação de responsabilidades:

-   **`Controller` (Camada de Apresentação):** Recebe as requisições HTTP do navegador, interage com a camada de serviço e retorna o nome da View (template Thymeleaf) a ser renderizada.
-   **`Service` (Camada de Negócio):** Orquestra a lógica de negócio da aplicação, validando regras e garantindo a integridade dos processos.
-   **`Repository` (Camada de Acesso a Dados):** Interface responsável pela comunicação com o banco de dados, utilizando o Spring Data JPA.
-   **`Model` (Camada de Domínio):** Contém as entidades JPA (`@Entity`) que mapeiam o modelo de dados relacional.

## 🛠️ Tecnologias Utilizadas

| Ferramenta | Versão/Tecnologia | Descrição |
| ------------------- | ----------------- | -------------------------------------------------- |
| **Linguagem** | Java | Versão 21+ |
| **Framework** | Spring Boot 3 | Framework para desenvolvimento da aplicação web |
| **View Layer** | Thymeleaf 3+ | Template Engine para renderização no servidor (SSR) |
| **Banco de Dados** | PostgreSQL | Gerenciado via **Docker** e com migrações **Flyway** |
| **Gerenciador** | Maven | Gerenciador de dependências e build do projeto |
| **Autenticação** | Spring Security | Autenticação e autorização baseada em Sessão |

## 🚀 Como Executar o Projeto

Este guia irá te ajudar a configurar e executar o ambiente de desenvolvimento localmente. Após a configuração inicial, a aplicação pode ser iniciada com um único comando.

### Pré-requisitos

Antes de começar, garanta que você tenha as seguintes ferramentas instaladas em sua máquina:

-   **Java JDK 21+** ([Download](https://www.oracle.com/java/technologies/downloads/))
-   **Docker** e **Docker Compose** ([Download Docker Desktop](https://www.docker.com/products/docker-desktop/))

O Maven será gerenciado pelo wrapper (`mvnw`) incluído no projeto.

### 1. Configuração Inicial (Apenas na primeira vez)

Estes passos são necessários apenas na primeira vez que você configurar o projeto.

```bash
# 1. Clone o repositório para sua máquina local
git clone [https://github.com/bmatox/petdoc-app.git](https://github.com/bmatox/petdoc-app.git)

# 2. Navegue para a pasta do projeto
cd petdoc-app
```

**Configuração do Banco de Dados:**

A aplicação precisa de um arquivo `application.properties` com as credenciais do banco.

1.  Navegue até a pasta `backend/src/main/resources/`.
2.  Crie uma cópia do arquivo `application.properties.example` (se ele existir) e renomeie a cópia para `application.properties`.
3.  Garanta que as credenciais no `application.properties` batem com as definidas no arquivo `.env` ou `docker-compose.yml`.

### 2. Executando com o Script Automatizado

Com a configuração inicial feita, para iniciar todo o ambiente (banco de dados + aplicação), basta executar o script correspondente ao seu sistema operacional a partir da **raiz do projeto**.

-   **No Windows:**
    ```bash
    .\iniciar.bat
    ```

-   **No Linux ou macOS:**
    ```bash
    # Dê permissão de execução ao script (apenas na primeira vez)
    chmod +x iniciar.sh

    # Execute o script
    ./iniciar.sh
    ```


O script irá automaticamente verificar suas dependências, iniciar o container do banco de dados e rodar a aplicação Spring Boot. A aplicação web estará acessível em **`http://localhost:8080`**.
