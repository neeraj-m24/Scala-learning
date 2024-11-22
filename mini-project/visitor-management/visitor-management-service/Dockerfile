# Use an official sbt image as a parent image
FROM hseeberger/scala-sbt:11.0.12_1.5.5_2.13.6

# Set the working directory in the container
WORKDIR /app

# Copy the project files
COPY . .

# Pre-fetch dependencies to speed up builds
RUN sbt clean compile

# Make port 8080 available to the world outside this container
EXPOSE 8081

# Define environment variable
ENV NAME = visitor-management-service
#ENV PLAY_SECRET_KEY = a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6a1b2c3d4e5f6g7h8a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6a1b2c3d4e5f6g7h8a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6a1b2c3d4e5f6g7h8

# Run the application
CMD ["sbt", "run"]