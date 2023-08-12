(ns build
  (:refer-clojure :exclude [test])
  (:require [clojure.tools.build.api :as b]
            [deps-deploy.deps-deploy :as dd]))

(def lib 'com.github.dvliman/logsnag)
(def version (format "0.0.%s" (b/git-count-revs nil)))
(def class-dir "target/classes")

(defn- jar-opts [opts]
  (assoc opts
         :lib lib :version version
         :jar-file (format "target/%s-%s.jar" lib version)
         :scm {:tag (str "v" version)}
         :basis (b/create-basis {})
         :class-dir class-dir
         :target "target"
         :src-dirs ["src"]))

(defn ci "Run the CI pipeline of tests (and build the JAR)." [opts]
  (b/delete {:path "target"})
  (let [opts (jar-opts opts)]
    (println "\nWriting pom.xml...")
    (b/write-pom opts)
    (println "\nCopying source...")
    (b/copy-dir {:src-dirs ["resources" "src"] :target-dir class-dir})
    (println "\nBuilding JAR...")
    (b/jar opts))
  opts)

(defn install "Install the JAR locally." [opts]
  (let [opts (jar-opts opts)]
    (b/install opts))
  opts)

(defn deploy "Deploy the JAR to Clojars." [opts]
  (let [{:keys [jar-file repository] :as opts} (jar-opts opts)]
    (dd/deploy
     (cond-> {:installer :remote :artifact (b/resolve-path jar-file)
              :pom-file (b/pom-path (select-keys opts [:lib :class-dir]))}
       (map? repository) (assoc :repository repository))))
  opts)
