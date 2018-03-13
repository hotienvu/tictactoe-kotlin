VERSION=1.0.0
DOCKER_IMAGE=tictactoe

binary:
	docker build -t tictactoe:build -f Dockerfile.build .
	docker create --name tictactoe tictactoe:build  /bin/bash
	docker cp tictactoe:/build/target/tic-tac-toe-1.0-SNAPSHOT-jar-with-dependencies.jar .
	docker rm tictactoe

docker:
	docker build --no-cache -t $(DOCKER_IMAGE):$(VERSION) .
	docker tag $(DOCKER_IMAGE):$(VERSION) $(DOCKER_IMAGE):latest

clean:
	rm -f *.jar
	docker rm tictactoe || true

all: clean binary docker
