package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.Entity.User;
import by.tms.dzen.yandexdzenrestc51.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username){
        if (username == null | userRepository.findByUsername(username).isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        User getUser = userRepository.findByUsername(username).get();
        return ResponseEntity.ok(getUser);
    }

    @PostMapping()
    public ResponseEntity<User> save(@Valid @RequestBody User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().build();
        }
        User save = userRepository.save(user);
        return ResponseEntity.ok(save);
    }

    @PatchMapping("/{username}")
    public ResponseEntity<User> update(@PathVariable("username") String username, @RequestBody User user){
        if (username == null | userRepository.findByUsername(username).isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        User update = userRepository.findByUsername(username).get();
        user.setId(update.getId());
        userRepository.save(user);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable("username") String username){
        if (username == null | userRepository.findByUsername(username).isEmpty()){
        }
        User user = userRepository.findByUsername(username).get();
        userRepository.delete(user);
    }



}
