.PHONY: repl
repl:
	clj -M:cider

.PHONY: run 
run:
	clj -M -m image-gen.core

.PHONY: deps
deps:
	clojure -e "(println \"Dependencies downloaded\")"
