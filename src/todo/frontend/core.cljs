(ns todo.frontend.core
  (:require [reagent.core :as r]
            [reagent.dom.client :as rdom]))

;; Primeiro componente visual
(defn hello-world []
  [:div
   [:h1 "Olá, Alunos!"]
   [:p "Nossa aplicação ClojureScript está funcionando."]])

;; Inicializa a interface no navegador
(defn ^:export init []
  (println "Frontend inicializado...")
  (let [root (rdom/create-root
              (js/document.getElementById "app"))]
    (.render root (r/as-element [hello-world]))))
