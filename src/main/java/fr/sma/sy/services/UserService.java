package fr.sma.sy.services;

import fr.sma.svc.sy.userservice.model.ListUserResponse;
import fr.sma.sy.entities.User;
import fr.sma.sy.mappers.ObjectMapperUtils;
import fr.sma.sy.repositories.UserDao;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UserService {
    private final UserDao userDao;
    private ObjectMapperUtils objectMapperUtils;
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    public ListUserResponse retrieveUsers() {
        List<User> reponseDTO  = this.userDao.getListUsers();
        List<fr.sma.svc.sy.userservice.model.User> listUers  =
        objectMapperUtils.mapAll(reponseDTO, fr.sma.svc.sy.userservice.model.User.class);
        ListUserResponse out = new ListUserResponse();
        out.addAll(listUers);
        return out;
    }
}
