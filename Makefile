.PHONY: build clean run

package:
	@ mvn clean package

docker-compose-up: package
	@ docker-compose up --build
