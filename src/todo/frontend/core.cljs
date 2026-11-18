(ns todo.frontend.core
  (:require [reagent.core :as r]
            [reagent.dom.client :as rdom]
            [clojure.string :as str]
            [cljs.core.async :refer [go]]
            [cljs.core.async.interop :refer-macros [<p!]]))

;; Estado reativo da aplicacao
(defonce app-state
  (r/atom {:input-text ""
           :todos []
           :loading false
           :error nil}))

(def api-url "http://localhost:3000/api")

;; Faz requisicoes HTTP e converte JSON
(defn fetch-json [url options]
  (-> (js/fetch url (clj->js options))
      (.then (fn [response]
               (when-not (.-ok response)
                 (throw
                  (js/Error.
                   (str "HTTP error: " (.-status response)))))
               (.json response)))
      (.then #(js->clj % :keywordize-keys true))))

;; Busca todas as tarefas do backend (GET)
(defn get-todos []
  (swap! app-state assoc :loading true :error nil)
  (go
    (try
      (let [response
            (<p! (fetch-json
                  (str api-url "/todos")
                  {:method "GET"}))]
        (swap! app-state
               assoc
               :todos (:todos response)
               :loading false))
      (catch js/Error e
        (swap! app-state
               assoc
               :error (.-message e)
               :loading false)))))

;; Cria uma tarefa no backend (POST)
(defn create-todo [todo-data]
  (swap! app-state assoc :loading true :error nil)
  (go
    (try
      (<p! (fetch-json
            (str api-url "/todos")
            {:method "POST"
             :headers {"Content-Type" "application/json"}
             :body (js/JSON.stringify
                    (clj->js todo-data))}))

      ;; Atualiza a lista apos o cadastro
      (get-todos)

      (catch js/Error e
        (swap! app-state
               assoc
               :error (.-message e)
               :loading false)))))


;; Alterna o status de uma tarefa pela API
(defn toggle-todo
  "Chama a API para alternar o status de um todo."
  [id]
  (go
    (try
      (<p! (fetch-json
            (str api-url "/todos/" id "/toggle")
            {:method "POST"}))

      ;; Atualiza a lista apos a alteracao
      (get-todos)

      (catch js/Error e
        (swap! app-state
               assoc
               :error (.-message e)
               :loading false)))))


;; Exclui uma tarefa pela API
(defn delete-todo
  "Chama a API para remover um todo."
  [id]
  (go
    (try
      (<p! (fetch-json
            (str api-url "/todos/" id)
            {:method "DELETE"}))

      ;; Atualiza a lista apos a exclusao
      (get-todos)

      (catch js/Error e
        (swap! app-state
               assoc
               :error (.-message e)
               :loading false)))))


;; Formulario de cadastro
(defn todo-form []
  [:div.todo-input
   [:input
    {:type "text"
     :placeholder "O que precisa ser feito?"
     :value (:input-text @app-state)
     :on-change #(swap! app-state
                        assoc
                        :input-text
                        (-> % .-target .-value))}]

   [:button
    {:disabled (:loading @app-state)
     :on-click
     (fn []
       (let [titulo (str/trim (:input-text @app-state))]
         (when-not (str/blank? titulo)
           (create-todo {:title titulo})
           (swap! app-state assoc :input-text ""))))}
    "Adicionar"]])


;; Lista de tarefas 
(defn todo-list []
  [:ul.todo-list
   (for [todo (:todos @app-state)]
     ^{:key (:todos/id todo)}

     [:li.todo-item
      {:class (when (= 1 (:todos/completed todo))
                "completed")}

      [:input.todo-checkbox
       {:type "checkbox"
        :checked (not= 0 (:todos/completed todo))
        :on-change #(toggle-todo (:todos/id todo))}]

      [:span.todo-title
       (:todos/title todo)]

      [:button.delete-btn
       {:on-click #(delete-todo (:todos/id todo))}
       "X"]])])


;; Componente principal
(defn app []
  [:div.todo-app
   [:h1 "Todo App"]
   [:p "Pequenas tarefas, grandes conquistas."]

   [todo-form]

   (when (:loading @app-state)
     [:p "Carregando..."])

   (when-let [erro (:error @app-state)]
     [:p {:style {:color "red"}}
      (str "Erro: " erro)])

   [todo-list]])

;; Inicializacao do frontend
(defn ^:export init []
  (println "Frontend inicializado...")
  (let [root (rdom/create-root
              (js/document.getElementById "app"))]
    (.render root (r/as-element [app])))

  (get-todos))
