package com.busra.todolist.user;

import com.busra.todolist.user.model.User;
import com.busra.todolist.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> body) {
        User user = new User();
        user.setName(body.get("name"));
        user.setLastName(body.get("lastname"));
        user.setUserName(body.get("username"));
        user.setPassword(body.get("password"));

        userRepository.save(user);

        return ResponseEntity.ok("success");
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        if (userRepository.findByUserName(username).getPassword().equals(password)) {
            return ResponseEntity.ok("success");
        } else {
            return new ResponseEntity<>("reject", HttpStatus.BAD_REQUEST);
        }
    }
}
