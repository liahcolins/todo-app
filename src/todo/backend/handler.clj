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
