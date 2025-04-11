# 🗂️ Projeto: Board de Tarefas

Este projeto é um sistema simples de gerenciamento de tarefas em um **Board Kanban**, desenvolvido em **Java** com uso de **Spring Framework**, **JDBC**, **Liquibase** e **MySQL**. Ele permite criar boards com colunas personalizadas, adicionar tarefas (cards), e aplicar bloqueios nas tarefas com motivo e data.

## 🔧 Tecnologias utilizadas

- Java 17
- Spring Framework (bootstrapping)
- JDBC puro (sem JPA)
- MySQL
- Liquibase (controle de versão do banco de dados)
- Maven

## 📐 Diagrama UML

Abaixo está o diagrama UML que representa as principais entidades do sistema e seus relacionamentos:

![Diagrama UML](https://raw.githubusercontent.com/caio20538/board/main/uml_img/Board.png)

### Entidades

- **Board**: Representa um quadro de tarefas. Cada board pode ter múltiplas colunas.
- **BoardColumn**: Colunas do board (por exemplo: A fazer, Em andamento, Concluído, Cancelado). Possui um tipo (`kind`) e uma ordem de exibição.
- **Card**: Representa uma tarefa no sistema. Cada card pertence a uma coluna e pode estar bloqueado.
- **Blocked**: Registra o histórico de bloqueios de um card, com motivo, data de bloqueio e desbloqueio.

### Relacionamentos

- Um **Board** pode ter uma ou mais (**1..n**) **BoardColumns**.
- Uma **BoardColumn** pode conter uma ou mais (**1..n**) **Cards**.
- Um **Card** pode ter um ou mais (**1..n**) registros de **Blocked**, representando bloqueios aplicados ao longo do tempo.

## 📁 Estrutura do Projeto
```plaintext
src/main/java/br/com/dio/Board/
├── dto/              # Data Transfer Objects (representações para comunicação entre camadas)
├── exceptions/       # Classes para exceções personalizadas
├── persistence/      # Camada de persistência de dados
│   ├── config/       # Configuração do banco (ex: datasource, conexão JDBC)
│   ├── dao/          # Data Access Objects (JDBC puro)
│   ├── entity/       # Entidades que representam as tabelas do banco
│   ├── migration/    # Scripts de versão do banco (Liquibase)
│   └── util/         # Utilitários como conversores e helpers
├── service/          # Lógica de negócio
├── ui/               # Interface com o usuário (CLI, menus, etc)
└── BoardApplication  # Classe principal (main) para rodar a aplicação
