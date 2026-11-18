
# Todo List — Clojure e ClojureScript

**Aluna:** Liah Renata Colins da Silva

**Repositório:** [github.com/liahcolins/todo-app](https://github.com/liahcolins/todo-app)

**Tutorial original:** [Clojure e ClojureScript: Construindo uma Aplicação Todo List do Zero](https://lambdageo-edu.github.io/lambdageo-tutorials/clojure/)

## Descrição

O Todo List é uma aplicação web full-stack desenvolvida com Clojure e ClojureScript, com o objetivo de estudar conceitos de programação funcional, desenvolvimento de APIs REST, interfaces reativas e persistência de dados.

O sistema permite gerenciar tarefas de forma simples, realizando operações de cadastro, listagem, atualização do estado de conclusão e exclusão.

O projeto foi desenvolvido de maneira incremental, seguindo as etapas do tutorial e utilizando Git e GitHub para o versionamento.

## Funcionalidades

- Cadastrar novas tarefas.
- Listar as tarefas cadastradas.
- Marcar tarefas como concluídas.
- Desmarcar tarefas concluídas.
- Excluir tarefas.
- Validar títulos vazios.
- Atualizar a interface automaticamente.
- Persistir os dados em SQLite, mesmo após reiniciar o servidor.

## Tecnologias Utilizadas

### Backend
- Clojure
- Ring e Jetty
- Reitit
- ring-json
- ring-cors

### Frontend
- ClojureScript
- Reagent 2.0
- React 19
- Shadow-CLJS
- HTML e CSS

### Banco de Dados
- SQLite
- next.jdbc

### Ferramentas
- Visual Studio Code
- Thunder Client
- Git e GitHub

## Pré-requisitos

Para executar a aplicação, é necessário ter instalado:

| Ferramenta | Versão |
|------------|--------|
| Java JDK | 11 ou superior |
| Clojure CLI | 1.11 ou superior |
| Node.js | 18 ou superior |
| npm | Compatível com o Node.js |
| Git | Instalado |

## Como Executar

### 1. Clonar o repositório

Abra um terminal e execute:

```powershell
git clone https://github.com/liahcolins/todo-app.git
cd todo-app
```

### 2. Instalar as dependências do frontend

Na raiz do projeto, execute:

```powershell
npm install
```

### 3. Iniciar o backend

Abra um terminal na raiz do projeto e execute:

```powershell
clojure -M:run
```

O servidor será iniciado em:

http://localhost:3000

Na primeira execução, as dependências do Clojure serão baixadas e o banco de dados `prod.db` será criado automaticamente.

### 4. Iniciar o frontend

Abra um segundo terminal na raiz do projeto e execute:

```powershell
npx shadow-cljs watch app
```

Aguarde a compilação ser concluída.

O frontend estará disponível em:

http://localhost:8000

### 5. Acessar a aplicação

Abra o navegador e acesse:

http://localhost:8000

Mantenha os dois terminais executando para utilizar todas as funcionalidades.

## Endpoints da API REST

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/hello` | Testa o funcionamento do servidor |
| GET | `/api/todos` | Lista todas as tarefas |
| POST | `/api/todos` | Cadastra uma nova tarefa |
| POST | `/api/todos/:id/toggle` | Alterna o estado de conclusão |
| DELETE | `/api/todos/:id` | Exclui uma tarefa |

### Exemplo de cadastro

**Requisição:**

```http
POST http://localhost:3000/api/todos
Content-Type: application/json
```

**Body JSON:**

```json
{
  "title": "Estudar Clojure",
  "description": "Revisar o conteúdo da disciplina"
}
```

A API retorna a tarefa cadastrada com seu identificador, estado de conclusão e data de criação.

## Estrutura do Projeto

```text
todo-app/
├── deps.edn
├── package.json
├── package-lock.json
├── shadow-cljs.edn
├── README.md
├── resources/
│   └── public/
│       └── index.html
└── src/
    └── todo/
        ├── backend/
        │   ├── core.clj
        │   ├── db.clj
        │   └── handler.clj
        └── frontend/
            └── core.cljs
```

## Persistência dos Dados

O projeto utiliza SQLite para armazenar as tarefas em um arquivo local chamado `prod.db`.

A tabela `todos` é criada automaticamente durante a inicialização do backend, caso ainda não exista.

Os dados permanecem armazenados mesmo após reiniciar a aplicação.

O arquivo `prod.db` não é versionado no Git, pois está incluído no `.gitignore`.

## Histórico de Desenvolvimento

O projeto foi desenvolvido em fases:

1. Configuração do ambiente e Git.
2. Criação do servidor backend.
3. Implementação da API REST em memória.
4. Criação do frontend com Reagent.
5. Integração entre frontend e backend.
6. Persistência de dados com SQLite.
7. Implementação do CRUD completo e estilização.
8. Documentação e preparação da entrega.

Cada etapa foi registrada em commits no GitHub.

## Referência

Projeto desenvolvido a partir do tutorial:

[LambdaGeo — Clojure e ClojureScript](https://lambdageo-edu.github.io/lambdageo-tutorials/clojure/)
