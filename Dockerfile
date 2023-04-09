# Specify the base image
FROM adoptopenjdk/openjdk11:alpine-jre

# Set the working directory
WORKDIR /app

# Copy the executable JAR file from the target directory to the container
COPY target/SampleCarApp-0.0.1-SNAPSHOT.jar .

# Expose the application port
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "SampleCarApp-0.0.1-SNAPSHOT.jar"]