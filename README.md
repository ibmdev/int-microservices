# Microservices avec Spring Boot 2.2 , java 8 et Gradle 6.3

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
    1 gradle build ou gradle build -x test (skip tests)
    2 gradlew bootRun
```

## Fichiers de propriétés et profile

```
   java -jar myMicroService.jar 
   -Dspring.profiles.active=dev // VM Options
   --spring.config.location=classpath:/tech.properties,classpath:/application-dev.yml // Programs Arguments
```

## Configuration d'une base de données embarquée H2 pour développement

* Créer dans le répertoire src/main/resources

    - un script schema-h2.sql pour créer la structure des tables
    - un script data-h2.sql pour insérer les données dans les tables

## Configuration de MyBatis 

* Dépendance Gradle dans le fichier build.gradle
```
   dependencies {
     implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:2.1.2'
   }
```
Pour utiliser MyBatis avec Spring, on a besoin d'un SqlSessionFactory et au moins d'une interface mapper
Le starter MtBatis pour Spring Boot va : 

* auto-détecter une datasource existante
* créer and enregistrer une instance d'un SqlSessionFactory en passant la DataSource en entrée en utilisant le SqlSessionFactoryBean
* créer et enregistrer une instance de SqlSessionTemplate
* autoscanner les mappers et les lier au SqlSessionTemplate puis les associer au context Spring pour pouvoir les injecter
    
    ```
    /* DATASOURCE */
    @Bean(name = "DataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource DataSource() {
        return DataSourceBuilder.create().build();
    }
    
    /*  SessionFactory */
     @Bean(name = "SessionFactory")
     public SqlSessionFactory SessionFactory(@Qualifier("DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
     }
     
     /*  DataSourceTransactionManager */
      @Bean(name = "TransactionManager")
      public DataSourceTransactionManager TransactionManager(@Qualifier("DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
      }
      
      /* SqlSessionTemplate  */
      @Bean(name = "SessionTemplate")
      public SqlSessionTemplate SessionTemplate(@Qualifier("SessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
      }
    
    ```
 ## Gestion des logs 
 
 * Configuration poste développeur et projet
 
    - Dans Intellij Idea, télécharger le plugin Lombok
    - Dans le fichier build.gradle, rajouter les dépendances suivantes : 
    
    ```
    
    compileOnly 'org.projectlombok:lombok:1.18.12'
  	annotationProcessor 'org.projectlombok:lombok:1.18.12'
  	testCompileOnly 'org.projectlombok:lombok:1.18.12'
  	testAnnotationProcessor 'org.projectlombok:lombok:1.18.12'
    
    ```   
    - Dans le fichier build.gradle, rajouter la configuration suivante : 
    
        ```
        configurations {
    	    compileOnly {
    		    extendsFrom annotationProcessor
    	    }
    	    testCompileOnly {
    		    extendsFrom annotationProcessor
    	    }
        }
        ```   
    L'utilisation de Lombok pour les logs permet de ne pas avoir à déclarer explicitement de loggers dans chaque classe.
    Il suffit d'annoter sa classe avec l'une de ces annotations pour pouvoir bénificier automatiquement d'un logger
    @Slf4j, @Log4j, @CommonsLog, ....
  
  * Configuration applicative
  
    - Dans une classe (exemple RestController), ajouter l'annotation @Slf4j
    
        ```
        @RestController
        @Slf4j
        public class UserController {
            private final UserService userService;
            public UserController(UserService userService) {
                this.userService = userService;
            }
            @GetMapping("api/users")
            public List<User> retrieveUsers() {
                // Le logger n'a pas besoin d'être déclaré
                log.info("Appel du Controller UserController");
                return  this.userService.retrieveUsers();
            }
        }
        ```   
     - Dans le fichier application.yaml, configurer le logger  : 
        
        ```
        logging:
            pattern:
                console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
            level:
                fr.sma.sy.tests: TRACE
        ``` 
 
Les différents de niveaux de logs par ordre de priorité sont : TRACE, DEBUG, INFO, WARN, ERROR
 
Documentation pour configuration : https://howtodoinjava.com/spring-boot2/logging/configure-logging-application-yml/


## Gradle et Swagger 

L'un des grands principes des microservices est le concept "Contract First Development".
Cela signifie qu'un microservice doit fournir des fonctionnalités métiers utiles sur la
base d'une spécification d'API.
Nous allons concevoir notre contrat de service API REST avec Swagger
Exemple : Création d'un service UserService qui retourne une liste d'utilisateurs
stockées en base de données.

* Etape 1 : Définition du contrat de Service au format yaml 

    ```
    swagger: "2.0"
    info:
      description: "Spécification du service User"
      version: "1.0.0"
      title: "Contrat API UserService"
    tags:
      - name: "UserService"
    paths:
      /users:
        get:
          tags:
            - "users"
          summary: "Récupérer liste des utilisateurs"
          description: "Description du service"
          operationId: "getUsers"
          consumes:
          - "application/json"
          produces:
          - "application/json"
          responses:
            200:
              schema:
                $ref: "#/definitions/ListUserResponse"
              description: "Liste des utilisateurs"
    definitions:
      ListUserResponse:
        type: "array"
        items:
          $ref: "#/definitions/User"
      User:
        type: "object"
        properties:
          id:
            type: "integer"
          firstName:
            type: "string"
          lastName:
            type: "string"
          email:
            type: "string"
    ```
  
* Etape 2 : Génération des stubs avec le plugin Swagger CodeGen
* Etape 3 : Implémentation du service et test d'intégration associé





Documentation 

https://github.com/int128/gradle-swagger-generator-plugin/blob/master/README.md
https://www.tutorialspoint.com/spring_boot/spring_boot_enabling_swagger2.htm
https://blog.arnoldgalovics.com/generating-stubs-with-swagger-codegen-and-gradle/


    
## Gradle et les tests unitaires et d'intégration et JMeter : 
    
 Documentation : https://docs.gradle.org/current/dsl/org.gradle.api.tasks.testing.Test.html
 
## Les Twelves Factors 

Les twelve factors sont une méthodologie regroupant un ensemble de principes de développement : 
1) Code Base : 
2) Dépendances
3) Configuration
4) Backing Services : 

Le terme de "Backing Services" se refère à tout service que l'application Twelve Factors consomme.
Exemple de Backing Services : les bases de données, les API REST.
Dans ce factor, on considère tous les backing services comme des ressources externes attachées à l'application.
L'objectif est de pouvoir changer la configuration de ces ressources
comme par exemple passer d'une base de données embarquées H2 pour les tests à une base de données DB2 pour la production
ou changer de version du contrat de service REST sans changer le code de l'application.
 


5) Build, release, run
6) Processes
7) Port Binding
8) Concurrence
9) Disponibilité
10) Parité environnement Développement / Production
11) Logs
12) Administration

Test en ligne de commande : java -jar sy-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=dev --spring.config.location=D:/work/projets/Microservices/git/int-microservices/src/main/resources/application-dev.yml 

