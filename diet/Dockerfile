# Use Java 17 runtime
FROM eclipse-temurin:17-jdk-alpine

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
RUN ./mvnw clean package -DskipTests

# Set dynamic port for Render
ENV PORT=8080
EXPOSE 8080

# Run jar
CMD ["java", "-jar", "target/*.jar"]
