#Versão slim para reduzir o tamanho da imagem
FROM openjdk:22-jdk-slim

VOLUME /tmp

COPY target/leads-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

# Verifica a versão do Java
RUN java -version

# Incluir usuário não root por segurança
RUN adduser --system --group appuser
USER appuser

ENTRYPOINT ["java"]
CMD ["-jar", "/app.jar"]