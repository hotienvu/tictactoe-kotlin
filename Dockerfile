FROM anapsix/alpine-java:8

RUN mkdir -p /opt/tictactoe/
WORKDIR /opt/tictactoe
ADD entrypoint.sh /opt/tictactoe/
ADD tic-tac-toe-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/tictactoe/
ENV JAR_FILE=/opt/tictactoe/tic-tac-toe-1.0-SNAPSHOT-jar-with-dependencies.jar
ENTRYPOINT ["/opt/tictactoe/entrypoint.sh"]
