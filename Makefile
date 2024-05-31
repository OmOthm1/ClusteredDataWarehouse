# Build the Maven project and Docker images
build:
	mvn clean package
	docker-compose build

# Start the services
up: build
	docker-compose up -d

# Stop the services
down:
	docker-compose down

# View the logs
logs:
	docker-compose logs -f app

# Remove resources created by up
clean:
	docker-compose down -v --rmi all
