.PHONY: deploy
deploy:
	clojure -T:build ci; clojure -T:build deploy
