(ns dvliman.logsnag
  (:require [clj-http.client :as http]
            [cheshire.core :as json]))

(def base-url "https://api.logsnag.com")
(def log-endpoint     (str base-url "/v1/log"))
(def insight-endpoint (str base-url "/v1/insight"))

(defn publish
  "Publish a new event to LogSnag

  Expects a :client of :token and :project
  and an :event map with the following keys:

  | key           | description                                                 |
  | --------------|-------------------------------------------------------------|
  | `channel`     | channel name, example: waitlist                             |
  | `event`       | event name, example: user joined                            |
  | `description` | event description, example: joe@example.com joined waitlist |
  | `icon`        | event icon (emoji), must be a single emoji, example: :rose: |
  | `tags`        | event tags, example: {username: mattie}                     |
  | `notify`      | send push notification                                      |
  | `parser`      | parser for description                                      |"
  [{:keys [token project] :as _client} event]
  (http/post log-endpoint {:headers {"Authorization" (str "Bearer " token)
                                     "Content-Type" "application/json"}
                           :body (json/encode (into {:project project} event))}))

(defn insight
  "Publish insight LogSnag

  Expects a :client of :token and :project
  and an :event map with the following keys:

  | key           | description                                                   |
  | --------------|---------------------------------------------------------------|
  | `title`       | insight title, example: user count                            |
  | `value`       | insight value, example: 100                                   |
  | `icon`        | insight icon (emoji), must be a single emoji, example: :rose: |"
  [{:keys [token project] :as _client} insight]
  (http/post insight-endpoint {:headers {"Authorization" (str "Bearer " token)
                                         "Content-Type" "application/json"}
                               :body (json/encode (into {:project project} insight))}))


(comment
  (publish
   {:token "26...0b"
    :project "my-project"}
   {:notify true
    :channel :waitlist
    :event "user joined"
    :description "joe@example.com joined waitlist"
    :icon ":rose:"})

  (insight
   {:token "26...0b"
    :project "my-project"}
   {:title "pageview"
    :value 1
    :icon ":rose:"}))
