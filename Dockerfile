# STAGE 1 - BUILD (compilação)
FROM eclipse-temurin:21-jdk AS builder

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos necessários para baixar dependências primeiro (cache de build)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Dá permissão de execução ao Maven Wrapper
RUN chmod +x ./mvnw

# Baixa todas as dependências do projeto (melhora performance em builds futuros)
RUN ./mvnw dependency:go-offline -B

# Copia o código-fonte
COPY src src

# Faz o build da aplicação (gera o .jar)
RUN ./mvnw clean package -DskipTests


# STAGE 2 - RUNTIME (execução)
FROM eclipse-temurin:21-jre

# Define o timezone para São Paulo (Brasil)
ENV TZ=America/Sao_Paulo

# Instala tzdata para garantir que o timezone funcione corretamente
RUN apt-get update && \
    apt-get install -y tzdata && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone && \
    apt-get clean

# Diretório da aplicação
WORKDIR /app

# Copia o .jar gerado na etapa de build
COPY --from=builder /app/target/pontodoc-0.0.1-SNAPSHOT.jar app.jar

# Comando para iniciar a aplicação Java
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/app/app.jar"]