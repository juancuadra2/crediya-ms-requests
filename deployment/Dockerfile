FROM eclipse-temurin:21-jdk-alpine

# Create app directory and user
RUN mkdir -p /app && \
    addgroup -g 1001 appgroup && \
    adduser -u 1001 -G appgroup -s /bin/sh -D appuser && \
    chown -R appuser:appgroup /app

VOLUME /tmp

# Copy the JAR file from the correct location
COPY applications/app-service/build/libs/CrediYaMsRequests.jar /app/CrediYaMsRequests.jar

# Set environment variables
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70.0 -Djava.security.egd=file:/dev/./urandom"
ENV SPRING_PROFILES_ACTIVE=docker

# Switch to non-root user for security
USER appuser

WORKDIR /app

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar CrediYaMsRequests.jar" ]
