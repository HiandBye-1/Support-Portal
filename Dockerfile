FROM  eclipse-temurin:21-jdk AS build

WORKDIR /Personal

COPY . .

RUN javac -cp "lib/*"  src/*.java 



FROM  eclipse-temurin:21-jdk
COPY --from=build /Personal /Personal

not working at the moment


ENTRYPOINT ["java", "-cp", "src:lib/*", "MainMenu"]

