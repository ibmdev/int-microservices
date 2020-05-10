package fr.sma.sy.repositories;

import fr.sma.sy.entities.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserDao {

    @Select("SELECT * from User")
    List<User> getListUsers();
}
