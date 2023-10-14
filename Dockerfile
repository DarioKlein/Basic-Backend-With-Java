# Estágio de compilação
FROM ubuntu:latest AS build

# Atualiza os repositórios e instala o OpenJDK 17
RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

# Copia o código-fonte da aplicação para o contêiner
COPY . .

# Instala o Maven e compila a aplicação
RUN apt-get install maven -y
RUN mvn clean install

# Estágio de produção
FROM openjdk:17-jdk-slim

# Expõe a porta 8080 (se a aplicação ou serviço utiliza essa porta)
EXPOSE 8080

# Copia o arquivo JAR da compilação do estágio de compilação
COPY --from=build /target/todolist-1.0.0.jar app.jar

# Comando de entrada para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
