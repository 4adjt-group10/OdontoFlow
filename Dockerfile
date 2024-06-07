# Use a imagem oficial do OpenJDK para criar uma imagem base
FROM openjdk:17-jdk-alpine

# Variáveis de ambiente para o diretório do aplicativo
ENV APP_DIR=/usr/app/

# Cria o diretório do aplicativo no container
RUN mkdir -p $APP_DIR

# Define o diretório do aplicativo como o diretório de trabalho
WORKDIR $APP_DIR

# Instala o Maven
RUN apk add --no-cache maven

# Copia o código fonte para o diretório do aplicativo
COPY src $APP_DIR/src
COPY pom.xml $APP_DIR

# Copia o arquivo jar para o diretório do aplicativo
COPY target/odontoflow-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta 8080 para acessar a aplicação
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["java", "-jar", "app.jar"]