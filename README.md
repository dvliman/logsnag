# Logsnag Clojure 

Clojure library for interacting with the [LogSnag](https://docs.logsnag.com/) API. Small, opinionated, and hand-crafted.

[![Clojars Project](https://img.shields.io/clojars/v/com.github.dvliman/logsnag.svg)](https://clojars.org/com.github.dvliman/logsnag) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Coordinates

```clojure
com.github.dvliman/logsnag {:mvn/version "0.0.1"}
```

## Usage
First, require the library. You can generate token on [Settings > API > My Tokens](https://app.logsnag.com/dashboard/settings/api) page

```clojure
(require '[dvliman.logsnag :as logsnag]')

(def logsnag-client 
  {:token "26...0b" :project "my-project"})

(logsnag/publish
  logsnag-client
  {:notify true
   :channel :waitlist
   :event "user joined"
   :description "joe@example.com joined waitlist"
   :icon ":rose:"})
    
(logsnag/insight
  logsnag-client 
  {:title "pageview"
   :value 1
   :icon ":rose:"})
```

## License

Copyright © 2023 dvliman.com 

Distributed under the Eclipse Public License version 1.0.
