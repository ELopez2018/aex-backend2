FROM  openjdk:19-alpine as builder

WORKDIR /app

COPY ./.mvn ./.mvn
COPY ./mvnw .
COPY ./pom.xml .
RUN chmod +x mvnw
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./src ./src

RUN ./mvnw clean package -DskipTests
FROM  openjdk:19-alpine

WORKDIR /app

COPY --from=builder /app/target/mscio-bank-transfer-0.0.1-SNAPSHOT.jar .
ENV PORT 7000
EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "./mscio-bank-transfer-0.0.1-SNAPSHOT.jar"]