FROM eclipse-temurin:21-jre

# Set working directory inside container
WORKDIR /app

# Copy the built JAR file from target folder
COPY target/*.jar app.jar

# Expose port 8080 for the application
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]