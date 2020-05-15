package fr.sma.sy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sma.svc.sy.userservice.api.UsersApi;
import fr.sma.svc.sy.userservice.api.UsersApiController;
import fr.sma.sy.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import fr.sma.svc.sy.userservice.model.ListUserResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@Slf4j
public class UserController implements UsersApi {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<ListUserResponse> getUsers() {
        log.info("Appel du Controller UserController");
        ListUserResponse users = this.userService.retrieveUsers();
        if(users !=null) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(users, HttpStatus.NOT_FOUND);
        }
    }

    /*
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("api/users")
    public ListUserResponse retrieveUsers() {
        log.info("Appel du Controller UserController");
        return  this.userService.retrieveUsers();
    }
    */
}
