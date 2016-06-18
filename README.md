# web-a-go-go
This is a Spring Boot web application exercise. And it's a game of Go :) 

This is also the first time I create a project on GitHub, so: Hello World!

# What you'll need
* [Git](https://git-scm.com/download/)
* [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or later
* [Maven 3.0+](http://maven.apache.org/download.cgi)
* [Redis](http://redis.io/download) (For Windows, see [here](https://github.com/MSOpenTech/redis/releases). You may need to change the user running the Redis service during installation process, and start the service again to successfully install Redis for Windows)
* [Spring Tool Suite](https://spring.io/tools/sts/all) or other capable IDE or your choice (optional). Pick the right version (64bit etc) or the STS will fail with a bit confusing error message

# Building and running
* Check out the source code by typing `git clone https://github.com/anttikarhu/web-a-go-go.git web-a-go-go`
* Go to the folder `cd web-a-go-go`
* Type `mvn spring-boot:run`
* Open [http://localhost:8080/](http://localhost:8080/) in your web browser

# About the project
This project has a Spring Boot backend, and an AngularJS frontend. The Spring Boot is at it's default configurations. The backend serves an html page along with the needed js and css files from an Embedded Tomcat, Spring Boot's default container, and these make up the UI frontend application running in the browser. Wro is used as a frontend dependency manager, just for good measure, and it's run on the Maven build. Game state is held at a Redis NoSQL database.

# TODOs and known issues
* This is just an excerise :)
* Game ending and scoring is not implemented
* Remove stones button should be removed
* makeMove has no integration test, so it's not necessarely perfectly robust
* Integration tests use the "live" Redis database, it should be mocked
* Error handling is not finished, for example UI does not show reasons for rejected moves
* suicide rule has not been implemented
