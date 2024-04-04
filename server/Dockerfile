# Use Maven with OpenJDK 17 to build the application
FROM maven:3.8.1-openjdk-17 as build

# Copy the source code to the container
COPY src /home/app/src
COPY pom.xml /home/app

# Package the application
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Use OpenJDK 17 slim for the runtime
FROM openjdk:17-slim

# Copy the built artifact from the build stage to the /app directory
COPY --from=build /home/app/target/server-0.0.1-SNAPSHOT.jar /app/server.jar

# Expose port 3000 for the web service
EXPOSE 3000

# Execute the application
CMD ["java", "-jar", "/app/server.jar"]
