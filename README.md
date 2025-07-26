
---

# ⚽ FutCamp\_API

> API RESTful para gerenciamento de campeonatos de futebol, permitindo controle de times, jogadores, partidas e resultados — com atualização dinâmica da tabel.

---

## 📌 Índice

* [Descrição](#descrição)
* [Funcionalidades](#funcionalidades)
* [Tecnologias](#tecnologias)
* [Requisitos](#requisitos)
* [Instalação](#instalação)
* [Como usar](#como-usar)
* [Endpoints principais](#endpoints-principais)
* [Desenvolvimento](#desenvolvimento)
* [Licença](#licença)

---

## 📖 Descrição

O **FutCamp\_API** é uma API desenvolvida em Java com Spring Boot para facilitar a organização de campeonatos de futebol. Permite cadastrar times, jogadores, estádios e partidas, além de registrar resultados e atualizar automaticamente a classificação.

---

## ✅ Funcionalidades

* 📋 Cadastro de **times**, **jogadores** e **campeonatos**
* 🏟️ Gerenciamento de **estádios** e locais de partidas
* 🆚 Registro de **partidas** (com mandante e visitante)
* 🥅 Lançamento de **resultados** e atualização automática da **tabela**
* 📊 Geração de **classificação** em tempo real
* 🔍 Consulta detalhada de jogos, clubes e estatísticas

---

## ⚙️ Tecnologias

* [Java 21+](https://www.oracle.com/br/java/)
* [Spring Boot 3.5+](https://spring.io/projects/spring-boot)
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* [Flyway](https://flywaydb.org/) (migração de banco de dados)
* [MySQL 8](https://www.mysql.com/)
* [Lombok](https://projectlombok.org/)
* [Maven](https://maven.apache.org/)

---

## 🧰 Requisitos

* Java 21 ou superior
* MySQL rodando localmente (ou URL configurada)
* Maven instalado
* Postman, Insomnia ou outro client REST (opcional)

---

## 🚀 Instalação

```bash
# Clone o repositório
git clone https://github.com/a-mais/FutCamp_API.git
cd FutCamp_API

# Configure o banco de dados em src/main/resources/application.properties

# Rode a aplicação
./mvnw spring-boot:run
```

---

## 🧪 Como usar

A aplicação expõe endpoints REST sob o caminho base:

```
http://localhost:8080/api/
```

Exemplos:

* `POST /api/times` — cria um novo time
* `GET /api/partidas` — lista todas as partidas
* `POST /api/resultados` — registra um resultado
* `GET /api/classificacao` — obtém a tabela atualizada

---

## 🔗 Endpoints principais

| Verbo | Rota                      | Descrição                              |
| ----- | ------------------------- | -------------------------------------- |
| GET   | `/api/times`              | Lista todos os times                   |
| POST  | `/api/times`              | Cadastra um novo time                  |
| GET   | `/api/campeonatos`        | Lista campeonatos                      |
| POST  | `/api/partidas`           | Registra nova partida                  |
| POST  | `/api/resultados`         | Lança placar da partida                |
| GET   | `/api/classificacao/{id}` | Retorna tabela de pontos do campeonato |

---

## 👨‍💻 Desenvolvimento

Este projeto é parte da disciplina **Linguagem de Programação Web - Projeto Final (6º período - SI)**, orientado à prática de construção de APIs completas com boas práticas REST e Git Flow.

---

## 📄 Licença

Este projeto é licenciado sob os termos da [MIT License](LICENSE).

---
