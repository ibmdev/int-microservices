package fr.sma.sy.controllers;

import fr.sma.sy.entities.User;
import fr.sma.sy.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("api/users")
    public List<User> retrieveUsers() {
       return  this.userService.retrieveUsers();
    }
}
