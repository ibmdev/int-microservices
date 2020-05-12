package fr.sma.sy.services;

import fr.sma.sy.entities.User;
import fr.sma.sy.repositories.UserDao;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    public List<User> retrieveUsers() {
        return this.userDao.getListUsers();
    }
}
