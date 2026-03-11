FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app

COPY backend/pom.xml .
COPY backend/src ./src

RUN mvn clean package -DskipTests

EXPOSE 8080

CMD ["java","-jar","target/infosales-0.0.1-SNAPSHOT.jar"]