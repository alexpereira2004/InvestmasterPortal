# Etapa 1: Build da aplicação usando Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos do projeto para dentro do container
COPY pom.xml .
COPY src ./src

# Rodar o build e gerar o .jar
RUN mvn clean package -DskipTests

# Etapa 2: Executar a aplicação em imagem menor (somente com JRE)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar o .jar gerado da etapa anterior
COPY --from=builder /app/target/portal-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta padrão do Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
