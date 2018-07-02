(ns graphql-fx.test-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [graphql-fx.core-test]))

(doo-tests 'graphql-fx.core-test)
