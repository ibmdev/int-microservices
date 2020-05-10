package fr.sma.sy.components;

import fr.sma.sy.entities.User;
import fr.sma.sy.repositories.UserDao;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Profile("dev")
public class DataInitializer implements ApplicationRunner {

    private final UserDao userDao;

    public DataInitializer(UserDao userDao) {
        this.userDao = userDao;
    }

    public void run(ApplicationArguments applicationArguments) throws Exception {
        List<User> users = userDao.getListUsers();
        System.out.println("Initialisation des données au démarrage de l'application : "+ users.size());
    }
}