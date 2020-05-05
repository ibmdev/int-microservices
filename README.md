# Microservices avec Spring Boot 2.2.6 , java 11 et Gradle 6.3

* Créer un fichier gradle.properties dans C:/Users/Badge_User

* Modifier le fichier settings.gradle du projet

```
pluginManagement {
    repositories {
        maven {
            url artifactory_URL +'/public'
            credentials {
                username = artifactory_USER
                password = artifactory_PASSWORD
            }
            allowInsecureProtocol true
        }
    }
}
rootProject.name = 'appName'

```

* Modifier le fichier build.gradle du projet

```
buildscript {
  repositories {
    maven {
        url artifactory_URL +'/public'
    }
  }
  dependencies {
    classpath "org.springframework.boot:spring-boot-gradle-plugin:2.2.6.RELEASE"
  }
}
......

repositories {
    maven {
        url artifactory_URL +'/public'
        allowInsecureProtocol true
    }
}

.......

apply plugin: "org.springframework.boot"

```

## Installation Projet

```
    1 gradle build
    2 gradlew bootRun
```
