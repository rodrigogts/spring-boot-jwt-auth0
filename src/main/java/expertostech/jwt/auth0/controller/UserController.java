package expertostech.jwt.auth0.controller;

import expertostech.jwt.auth0.model.UserModel;
import expertostech.jwt.auth0.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/save")
    public ResponseEntity<UserModel> save(@RequestBody UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return new ResponseEntity<>(repository.save(user), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserModel>> findAll() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/passwordGen")
    public ResponseEntity<String> passwordGen(@RequestParam(name = "password") String password) {
        return ResponseEntity.ok(passwordEncoder.encode(password));
    }
}
