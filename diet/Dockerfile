# =========================
# 1️⃣ Build Stage
# =========================
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy project files
COPY pom.xml ./
COPY mvnw ./
COPY .mvn .mvn
COPY src ./src

# Give mvnw execution permission
RUN chmod +x ./mvnw

# Build project and skip tests
RUN ./mvnw clean package -DskipTests -Dmaven.test.skip=true

# =========================
# 2️⃣ Runtime Stage
# =========================
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/demo.jar demo.jar

# Expose port dynamically for Render
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar demo.jar --server.port=$PORT"]

