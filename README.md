
## 



## how to run

Requires Oracle or OpenJDK 8.

First, clone repo to local disk:

```bash
./gradlew assemble -Dgrails.env=development
cd build/libs
java -jar ./hello-vesta-questions-0.1.jar
```

Then go to [localhost:8080](http://localhost:8080) and you will see a basic homepage that contains a list of controllers.


## environment

This application is developed under:
- jdk: 8.0.242.hs-adpt
- grails: 4.0.2
- groovy: 2.5.x


## application setup

```bash
grails create-app hello-vesta-questions
```


