
(ns todo.backend.db)

;; Armazena as tarefas em memória.
(def todos-db (atom {}))

;; Controla os IDs das tarefas.
(def next-id (atom 0))

;; Retorna todas as tarefas cadastradas.
(defn get-all-todos []
  (vec (vals @todos-db)))

;; Cria uma nova tarefa e a adiciona ao banco.
(defn create-todo [todo-data]
  (let [id (swap! next-id inc)
        new-todo (merge todo-data
                        {:id id
                         :completed false
                         :created-at (str (java.time.Instant/now))})]
    (swap! todos-db assoc id new-todo)
    new-todo))
