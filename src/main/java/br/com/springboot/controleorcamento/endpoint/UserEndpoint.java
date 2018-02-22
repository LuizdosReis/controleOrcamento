package br.com.springboot.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.model.User;
import br.com.springboot.controleorcamento.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/user")
public class UserEndpoint {

    private final UserService userService;

    @Autowired
    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid User user){
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getByName(@PathVariable("username") String name){
        return new ResponseEntity<>(userService.loadUserByUsername(name),HttpStatus.OK);
    }

}
