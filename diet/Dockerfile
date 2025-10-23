# Use Java 17 runtime
FROM eclipse-temurin:21-jdk AS build


# Set working directory
WORKDIR /app

# Copy Maven files first to cache dependencies
COPY pom.xml ./
COPY mvnw ./
COPY .mvn .mvn





# Give execution permission to mvnw
RUN chmod +x ./mvnw


# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy all project files
COPY src ./src

# Build project
RUN ./mvnw clean package -DskipTests -Dmaven.test.skip=true
COPY --from=build /app/target/diet-0.0.1-SNAPSHOT.jar app.jar


# Set dynamic port for Render
ENV PORT=8080
EXPOSE 8080

# Run jar
CMD ["java", "-jar", "target/*.jar"]
