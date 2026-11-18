(ns todo.backend.db
  "Este namespace gerencia os dados dos 'todos'
   usando um banco de dados persistente SQLite."
  (:require [next.jdbc :as jdbc]))

;; Configuracao do banco de dados
(def db-spec
  {:dbtype "sqlite"
   :dbname "prod.db"})

;; Inicializa o banco de dados
(defn initialize-database!
  "Cria a tabela 'todos' se ela nao existir."
  []
  (jdbc/execute! db-spec ["
    CREATE TABLE IF NOT EXISTS todos (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      title TEXT,
      description TEXT,
      completed BOOLEAN DEFAULT 0,
      created_at TEXT
    )
  "]))

;; Retorna todas as tarefas cadastradas
(defn get-all-todos
  "Retorna todas as tarefas do banco."
  []
  (jdbc/execute! db-spec
                 ["SELECT * FROM todos ORDER BY created_at DESC"]))

;; Cadastra uma nova tarefa
(defn create-todo
  "Cria uma tarefa, salva no banco e retorna seus dados."
  [todo-data]
  (jdbc/execute-one! db-spec ["
    INSERT INTO todos (title, description, completed, created_at)
    VALUES (?, ?, ?, ?)
    RETURNING *"
    (:title todo-data)
    (:description todo-data)
    0
    (str (java.time.Instant/now))]))
