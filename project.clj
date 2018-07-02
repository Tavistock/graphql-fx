(defproject graphql-fx "0.0.1"
  :description "A re-frame library for graphql requests"
  :url "https://github.com/Tavistock/graphql-fx"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.228"]
                 [cljs-ajax "0.7.3"]
                 [day8.re-frame/http-fx "0.1.6"]
                 [vincit/venia "0.2.5"]
                 [camel-snake-kebab "0.4.0"]
                 [re-frame "0.10.5"]]
  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-doo "0.1.6"]]
  :doo {:paths {:rhino "lein run -m org.mozilla.javascript.tools.shell.Main"}}
  :aliases {"test" ["with-profile" "test" "doo" "rhino" "test" "once"]}
  :profiles
  {:test {:dependencies [[org.mozilla/rhino "1.7.7"]]
          :cljsbuild
          {:builds
           {:test
            {:source-paths ["src" "test"]
             :compiler {:output-to "target/main.js"
                        :output-dir "target"
                        :main graphql-fx.test-runner
                        :optimizations :simple}}}}}})
