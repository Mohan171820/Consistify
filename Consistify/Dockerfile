FROM eclipse-temurin:21-jdk-alpine


WORKDIR /app

# Copy everything first
COPY . .

# Give execute permission to mvnw
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-Dspring.profiles.active=prod", "-jar", "target/Consistify-0.0.1-SNAPSHOT.jar"]
