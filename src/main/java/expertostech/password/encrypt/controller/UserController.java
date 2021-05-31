package expertostech.password.encrypt.controller;

import expertostech.password.encrypt.model.UserModel;
import expertostech.password.encrypt.repository.UserRepository;
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

//    @PostMapping("/login")
//    public ResponseEntity<Boolean> login(@RequestBody UserModel data) {
//
//        UserModel user = repository.findByLogin(data.getLogin()).orElse(null);
//        if (user == null) {
//            return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
//        }
//
//        boolean ok = passwordEncoder.matches(data.getPassword(), user.getPassword());
//        HttpStatus status = ok ? HttpStatus.OK : HttpStatus.FORBIDDEN;
//
//        return new ResponseEntity<>(ok, status);
//    }
}
