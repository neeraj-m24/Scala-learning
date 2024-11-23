cd equipment-allocation
docker build -t equipment-allocation:latest .

cd ..
cd notification-service
docker build -t notification-service:latest .

cd ..
cd MessageProcessingService
docker build -t message-processing-service:latest .

docker network create kafka-network
