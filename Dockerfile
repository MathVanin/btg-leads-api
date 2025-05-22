#Versão slim para reduzir o tamanho da imagem
FROM openjdk:22-jdk-slim

VOLUME /tmp

COPY target/leads-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

# Vverifica versão do java e inclui usuário não root por segurança
RUN java -version adduser --system --group appuser
USER appuser

ENTRYPOINT ["java"]
CMD ["-jar", "/app.jar"]