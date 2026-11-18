(ns todo.backend.handler
  (:require [clojure.string :as str]
            [todo.backend.db :as db]))

(defn hello-handler [_request]
  {:status 200
   :headers {"Content-Type" "text/plain; charset=utf-8"}
   :body "Hello, World!"})

;; Lista todas as tarefas.
(defn list-todos-handler [_request]
  {:status 200
   :body {:todos (db/get-all-todos)}})

;; Recebe e cadastra uma nova tarefa.
(defn create-todo-handler [request]
  (let [todo-data (:body request)
        title (:title todo-data)]

    (if (and (string? title)
             (not (str/blank? title)))

      (let [new-todo (db/create-todo todo-data)]
        {:status 201
         :body new-todo})

      {:status 400
       :body {:error "O 'titulo' (title) e obrigatorio"}})))


;; Handler para alternar o status de uma tarefa
(defn toggle-todo-handler
  "Handler para a rota POST /api/todos/:id/toggle."
  [request]
  (let [id (Integer/parseInt
            (get-in request [:path-params :id]))]

    (if-let [updated-todo (db/toggle-todo! id)]
      {:status 200
       :body updated-todo}

      {:status 404
       :body {:error "Todo não encontrado"}})))

;; Handler para excluir uma tarefa
(defn delete-todo-handler
  "Handler para a rota DELETE /api/todos/:id."
  [request]
  (let [id (Integer/parseInt
            (get-in request [:path-params :id]))]

    (if-let [deleted-todo (db/delete-todo! id)]
      {:status 200
       :body deleted-todo}

      {:status 404
       :body {:error "Todo não encontrado"}})))

