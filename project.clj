(defproject guestbook "0.1.0-SNAPSHOT"

  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :dependencies [[clj-time "0.14.0"]
                 [bouncer "1.0.1"]
                 [com.h2database/h2 "1.4.193"]
                 [compojure "1.6.0"]
                 [conman "0.6.7"]
                 [cprop "0.1.10"]
                 [funcool/struct "1.0.0"]
                 [luminus-immutant "0.2.3"]
                 [luminus-migrations "0.3.9"]
                 [luminus-nrepl "0.1.4"]
                 [luminus/ring-ttl-session "0.3.2"]
                 [markdown-clj "0.9.99"]
                 [metosin/muuntaja "0.3.1"]
                 [metosin/ring-http-response "0.9.0"]
                 [mount "0.1.11"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [org.clojure/tools.logging "0.4.0"]
                 [org.webjars.bower/tether "1.4.0"]
                 [org.webjars/bootstrap "4.0.0-alpha.5"]
                 [org.webjars/font-awesome "4.7.0"]
                 [org.webjars/jquery "3.2.1"]
                 [ring-webjars "0.2.0"]
                 [ring/ring-core "1.6.2"]
                 [ring/ring-defaults "0.3.0"]
                 [selmer "1.10.9"]
				 [cljs-ajax "0.5.2"]
				 [org.clojure/clojurescript "1.7.228" :scope "provided"]
				 [reagent "0.5.1"]
				 [com.taoensso/sente "1.8.0"]]

  :min-lein-version "2.0.0"

  :jvm-opts ["-server" "-Dconf=.lein-env"]
  :source-paths ["src/clj"]
  
  :main ^:skip-aot guestbook.core
  :migratus {:store :database :db ~(get (System/getenv) "DATABASE_URL")}

  :plugins [[lein-cprop "1.0.3"]
            [migratus-lein "0.4.9"]
			[lein-cljsbuild "1.1.1"]]
  :resource-paths ["resources" "target/cljsbuild"]
  :target-path "target/%s/"
  :cljsbuild
  {:builds {:app {:source-paths ["src/cljs"]
                  :compiler {:output-to      "target/cljsbuild/public/js/app.js"
				             :output-dir     "target/cljsbuild/public/js/out"
							 :main "guestbook.core"
							 :asset-path "/js/out"
							 :optimizations :none
							 :source-map true
							 :pretty-print true}}}}
   :clean-targets
   ^{:protect false}
   [:target-path
     [:cljsbuild :builds :app :compiler :output-dir]
	 [:cljsbuild :builds :app :complier :output-to]]

  :profiles
  {:uberjar {:omit-source true
             :aot :all
             :uberjar-name "guestbook.jar"
             :source-paths ["env/prod/clj"]
             :resource-paths ["env/prod/resources"]}

   :dev           [:project/dev :profiles/dev]
   :test          [:project/dev :project/test :profiles/test]

   :project/dev  {:dependencies [[prone "1.1.4"]
                                 [ring/ring-mock "0.3.1"]
                                 [ring/ring-devel "1.6.2"]
                                 [pjstadig/humane-test-output "0.8.2"]]
                  :plugins      [[com.jakemccrary/lein-test-refresh "0.19.0"]]
                  
                  :source-paths ["env/dev/clj"]
                  :resource-paths ["env/dev/resources"]
                  :repl-options {:init-ns user}
                  :injections [(require 'pjstadig.humane-test-output)
                               (pjstadig.humane-test-output/activate!)]}
   :project/test {:resource-paths ["env/test/resources"]}
   :profiles/dev {}
   :profiles/test {}})
