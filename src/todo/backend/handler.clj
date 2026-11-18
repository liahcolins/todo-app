
(ns todo.backend.handler)

(defn hello-handler
  [_request]
  {:status 200
   :headers {"Content-Type" "text/plain; charset=utf-8"}
   :body "Hello, World!"})
