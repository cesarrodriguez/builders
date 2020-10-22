FROM adoptopenjdk/openjdk11
ENV TZ=America/Sao_Paulo
RUN echo ${TZ} > /etc/timezone
ADD target/*.jar /app/builders.jar
CMD ["java", "-jar", "/app/builders.jar"]
