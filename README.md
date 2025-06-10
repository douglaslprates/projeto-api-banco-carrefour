# 🚀 Automação de Testes - API ServeRest

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![RestAssured](https://img.shields.io/badge/RestAssured-5.3.0-green)
![Maven](https://img.shields.io/badge/Maven-3.8.6-orange)
![Allure Report](https://img.shields.io/badge/Allure_Report-2.24.0-ff69b4)

Projeto de automação de testes para a API [ServeRest](https://serverest.dev), implementando testes funcionais e não-funcionais com Java e RestAssured.

## 📋 Pré-requisitos

- JDK 11+
- Maven 3.8+
- Docker (opcional para execução em container)
- Allure CLI (para relatórios locais)

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Java 11
- **Framework de Testes**: JUnit 5
- **Cliente HTTP**: RestAssured
- **Relatórios**: Allure Report
- **CI/CD**: Jenkins (com Docker)
- **Gerenciamento de Dependências**: Maven

## 🔧 Configuração

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/serverest-api-tests.git
   
2. Instale as dependências:
   ```bash
    mvn clean install



🚀 Execução dos Testes

1. Executar todos os testes:
   ```bash
    mvn test

2. Executar testes específicos:
   ```bash
    mvn test -Dtest=UserCrudTest
   
3. Gerar relatório Allure:
   ```bash
    mvn allure:serve


📊 Relatórios

1. Para visualizar:
   ```bash
    allure serve target/allure-results


🐳 Execução com Docker

1. Suba os containers:
   ```bash
    docker-compose up -d

2. Acesse:

Jenkins: http://localhost:8080

Allure Report: http://localhost:5050



🔄 Pipeline CI/CD

O pipeline inclui:

- Build do projeto
- Execução de testes
- Geração de relatórios