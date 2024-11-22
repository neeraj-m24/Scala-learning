cd equipment-allocation
docker build -t equipment-allocation:latest .

cd ..
cd notification-service
docker build -t equipment-management:latest .

cd ..

docker network create kafka-network
