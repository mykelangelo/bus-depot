[Online App](https://bus-depot.herokuapp.com/go-home)
[![Codeship Status for mykelangelo/bus-depot](https://app.codeship.com/projects/ba87f830-09f1-0137-2b83-7a7d986a7277/status?branch=master)](https://app.codeship.com/projects/326158)
[![Dependabot](https://api.dependabot.com/badges/status?host=github&repo=mykelangelo/bus-depot)](https://dependabot.com)
[![Uptime Robot status](https://img.shields.io/uptimerobot/status/m782101237-8ef226930f56858f706c40fe.svg)](https://stats.uptimerobot.com/7AWoLi0J9)
[![Snyk](https://snyk.io/test/github/mykelangelo/bus-depot/badge.svg?targetFile=build.gradle)](https://snyk.io/test/github/mykelangelo/bus-depot?targetFile=build.gradle)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=mykelangelo_bus-depot&metric=alert_status)](https://sonarcloud.io/dashboard?id=mykelangelo_bus-depot) 

# Bus Depot (_№11_): how it works

- Drivers and Administrators can log into the system. There are buses and routes too.

- An administrator can assign free buses to the routes, free drivers to the buses, as well as vacate drivers. 

- A driver can see his place of work, and he must also confirm his new appointment.

# How to run locally
a) with docker pre-installed
 1. clone [this][repo]
 2. run `./gradlew clean startDependencies appRun`

b) with mysql pre-installed
 1. clone [this][repo] repository
 2. set up mysql as in /src/main/resources/connection-pool.properties
 3. run `./gradlew clean appRun`

 
# How to access online
 1. visit https://bus-depot.herokuapp.com/login
 2. choose language
 3. use any of following sign in method:
 - credentials for admin role:
    - email: `HAL9000@bus-depot.com`
    - password: `ImSorryDaveImAfraidICantDoThat`
 - for driver role:
    - choose credentials from [migration script](https://github.com/mykelangelo/bus-depot/blob/master/src/main/resources/db/production-data/migration/afterMigrate.sql)
    or
    - _while being logged in as an admin_ **add new driver** with new email and password and use these credentials to log in as that driver


# How to run tests
a) with docker pre-installed
 1. clone [this][repo]
 2. run `./gradlew clean startDependencies build`

b) with mysql pre-installed
 1. clone [this][repo] repository
 2. set up mysql as in /src/main/resources/connection-pool.properties
 3. run `./gradlew clean build`


# How to debug in Intellij Idea while running app
(One can easily debug tests, but with running app on tomcat it's tricky)
 1. run gradle command appRunDebug
 2. create `Remote` configuration in the IDEA
 3. click `debug` on this new configuration


[repo]: https://github.com/mykelangelo/bus-depot