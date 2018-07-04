# Graphql-fx

[![Clojars Project](https://img.shields.io/clojars/v/tavistock/graphql-fx.svg)](https://clojars.org/tavistock/graphql-fx)

A library for making Graphql request from a clojure re-frame app. Uses a similar interface as [re-frame-http-fx](https://github.com/Day8/re-frame-http-fx/) but accepts graphql queries in the [venia](https://github.com/Vincit/venia) format. Converts from the camelCase of GraphqQL to kebab-case for free or specify your own transfroms.

## Usage

either import and use the builtin `:graphql-xhrio` or make your own fx.

``` clojure
;; to use the bultin
(ns myns.core
  (:require [tavistock.graphql-fx]))
  ;; ... use {:graphql-xhrio {:query {:venia/queries [...]}}} as an effect

;; or use the underlying fn to make your own fx
(ns myns.core
  (:require [tavistock.graphql-fx :refer [graph-effect]
           [re-frame.core :refer [reg-fx]]))

(defn new-fx [request]
  (graph-effect ...))

(reg-fx :new-fx new-fx)
```
