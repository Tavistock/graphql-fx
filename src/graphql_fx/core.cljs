(ns graphql-fx.core
  (:require [clojure.walk :refer [postwalk]]
            [ajax.core :as ajax]
            [camel-snake-kebab.core :refer [->kebab-case ->camelCase]]
            [camel-snake-kebab.extras :refer [transform-keys]]
            [day8.re-frame.http-fx :refer [ajax-xhrio-handler]]
            [re-frame.core :refer [reg-fx dispatch]]
            [venia.core :refer [graphql-query]]))

(defn transform-keywords [transform coll]
  (postwalk (fn [x] (if (keyword? x) (transform x) x)) coll))

(defn graphql->xhrio-options
  [{:as request
    :keys [query variables
           on-success on-failure
           transform transform-response]
    :or {on-success         [:graphql-no-on-success]
         on-failure         [:graphql-no-on-failure]
         transform          ->camelCase
         transform-response ->kebab-case}}]
  (let [query (->> query
                   (map (fn [[k v]]
                          [(keyword "venia" (name k))
                           (transform-keywords transform v)]))
                   (into {})
                   graphql-query)
        api (new js/goog.net.XhrIo)]
    (-> request
        (assoc :method          :post
               :format          (ajax/json-request-format)
               :response-format (ajax/json-response-format {:keywords? true})
               :params          {:query query :variables variables}
               :api             api
               :handler
               (partial ajax-xhrio-handler
                        #(dispatch (conj on-success
                                         (transform-keys ->kebab-case %)))
                        #(dispatch (conj on-failure %))
                        api))
        (dissoc :query
                :variables
                :on-success
                :on-failure
                :transform
                :transform-response))))

(defn graph-effect
  [request]
  (let [seq-request-maps (if (sequential? request) request [request])]
    (doseq [request seq-request-maps]
      (-> request graphql->xhrio-options ajax/ajax-request))))

(reg-fx :graphql-xhrio graph-effect)
