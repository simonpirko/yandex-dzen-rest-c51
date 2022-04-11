package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.Entity.User;
import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.exception.UserNotFoundException;
import by.tms.dzen.yandexdzenrestc51.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@Api(tags = "User", description = "Operation about user")
@RequestMapping("/user")
public class userController {

    @Autowired
    private UserRepository userRepository;

    @ApiResponse(responseCode = "200", description = "successful operation")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiOperation(value = "Get user by user name")
    @GetMapping(value = "/{username}",produces = "application/json" )
    public ResponseEntity<User> getUser(@ApiParam(value = "The name that needs to be fetched. Use user1 for testing.",example = "username")@PathVariable("username") String username){
        if (username == null | userRepository.findByUsername(username).isEmpty()){
            throw new UserNotFoundException();
        }
        User getUser = userRepository.findByUsername(username).get();
        return ResponseEntity.ok(getUser);
    }

    @ApiResponse(responseCode = "200", description = "successful operation")
    @ApiResponse(responseCode = "405", description = "Invalid input")
    @ApiOperation(value = "Create user",notes = "This can only be done by the logged in user.")
    @PostMapping(produces = "application/json" )
    public ResponseEntity<User> save(@ApiParam(value = "Created user object", name="body")@Valid @RequestBody User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new InvalidException();
        }
        User save = userRepository.save(user);
        return ResponseEntity.ok(save);
    }

    @ApiResponse(responseCode = "200", description = "successful operation")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "405", description = "Invalid input")
    @ApiOperation(value = "Updated user", notes = "This can only be done by the logged in user.")
    @PatchMapping(value = "/{username}",produces = "application/json" )
    public ResponseEntity<User> update(@ApiParam(value = "username that need to be updated",example = "username")@PathVariable("username") String username, @ApiParam(value = "Updated user object",example = "user")@Valid @RequestBody User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new InvalidException();
        }
        if (username == null | userRepository.findByUsername(username).isEmpty()){
            throw new UserNotFoundException();
        }
        User update = userRepository.findByUsername(username).get();
        user.setId(update.getId());
        userRepository.save(user);
        return ResponseEntity.ok(update);
    }
    @ApiResponse(responseCode = "200", description = "successful operation")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiOperation(value = "Delete user", notes = "This can only be done by the logged in user.")
    @DeleteMapping(value = "/{username}",produces = "application/json" )
    public void deleteUser(@ApiParam(value = "username that need to be deleted",example = "username")@PathVariable("username") String username){
        if (username == null | userRepository.findByUsername(username).isEmpty()){
            throw new UserNotFoundException();
        }
        User user = userRepository.findByUsername(username).get();
        userRepository.delete(user);
    }



}
