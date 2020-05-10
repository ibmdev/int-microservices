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
    1 gradle build
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