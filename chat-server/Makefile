ENV := $(if ${env},${env},"test")
SRC_MAIN := "com.tenchael.chat.ChatServer"
export SRC_MAIN

.PHONY: build
build: ## package
	@mvn clean package

.PHONY: serve
serve: ## run server
	@mvn compile exec:java -Dexec.mainClass=$(SRC_MAIN)


.PHONY: exec
exec: ## execute server, equals with make serve
	@mvn clean compile exec:java