FROM amazoncorretto:21 AS builder

RUN yum update -y && yum install -y tar gzip

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x ./mvnw

RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw clean package -DskipTests

FROM amazoncorretto:21

COPY --from=builder target/pontodoc-0.0.1-SNAPSHOT.jar application.jar

ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/application.jar"]