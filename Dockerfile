# Etapa de build
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copia arquivos do Maven Wrapper e pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline -B

# Copia o código-fonte
COPY src src

# Compila o projeto
RUN ./mvnw clean package -DskipTests

# Etapa final (runtime)
FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=builder /app/target/pontodoc-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/app/app.jar"]
