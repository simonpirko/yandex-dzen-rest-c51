package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.entity.Subscriber;
import by.tms.dzen.yandexdzenrestc51.entity.User;
import by.tms.dzen.yandexdzenrestc51.exception.ExistsException;
import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.exception.NotFoundException;
import by.tms.dzen.yandexdzenrestc51.repository.SubscriberRepository;
import by.tms.dzen.yandexdzenrestc51.repository.UserRepository;
import by.tms.dzen.yandexdzenrestc51.service.Impl.UserService;
import by.tms.dzen.yandexdzenrestc51.validator.IdValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "User", description = "Operation about user")
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final IdValidator idValidator;
    private final SubscriberRepository subscriberRepository;

    public UserController(UserService userService,
                          UserRepository userRepository,
                          IdValidator idValidator,
                          SubscriberRepository subscriberRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.idValidator = idValidator;
        this.subscriberRepository = subscriberRepository;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @ApiOperation(value = "Get user by user name", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/{username}", produces = "application/json")
    public ResponseEntity<User> get(@ApiParam(value = "The name that needs to be fetched", example = "username")
                                    @PathVariable("username") String username) {

        if (username == null | userRepository.findByUsername(username).isEmpty()) {
            throw new NotFoundException();
        }
        User getUser = userRepository.findByUsername(username).get();

        return ResponseEntity.ok(getUser);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "405", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    @ApiOperation(value = "Create user", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @PostMapping(produces = "application/json")
    public ResponseEntity<User> save(@ApiParam(value = "Created user object", example = "User")
                                     @Valid @RequestBody User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ExistsException();
        }

        User save = userRepository.save(user);


        return ResponseEntity.ok(save);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Updated user", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/{username}", produces = "application/json")
    public ResponseEntity<User> update(@ApiParam(value = "username that need to be updated", example = "username")
                                       @PathVariable("username") String username,
                                       @ApiParam(value = "Updated user object", example = "User")
                                       @Valid @RequestBody User user,
                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        if (username == null | userRepository.findByUsername(username).isEmpty()) {
            throw new NotFoundException();
        }

        User update = userRepository.findByUsername(username).get();
        user.setId(update.getId());
        user.setRoleList(update.getRoleList());
        user.setStatus(update.getStatus());
        user.setPosts(update.getPosts());
        user.setSubscriberList(update.getSubscriberList());
        userService.update(user);

        return ResponseEntity.ok(update);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @ApiOperation(value = "Delete user", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/{username}", produces = "application/json")
    public void deleteUser(@ApiParam(value = "username that need to be deleted", example = "username")
                           @PathVariable("username") String username) {

        if (username == null | userRepository.findByUsername(username).isEmpty()) {
            throw new NotFoundException();
        }

        User user = userRepository.findByUsername(username).get();
        userService.delete(user);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Adding a subscriber to a user", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PostMapping(value = "/{idUser}/subscriber/{idSubscriber}", produces = "application/json")
    private void addSubscriberUser(@ApiParam(value = "user id is required to add subscribers", example = "1")
                                                   @PathVariable("idUser") Long idUser,
                                                   @ApiParam(value = "Subscriber id is required to add a subscriber " +
                                                           "to a user", example = "2")
                                                   @PathVariable("idSubscriber") Long idSubscriber) {
        idValidator.validateUserId(idUser);
        idValidator.validateUserId(idSubscriber);

        if (idUser.equals(idSubscriber)) {
            throw new InvalidException();
        }
        userService.addSubscriberUser(idUser, idSubscriber);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @ApiOperation(value = "Getting all subscribers of a user", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/{idUser}/subscriber", produces = "application/json")
    private ResponseEntity<List<Subscriber>> getAllSubscribersUser(@ApiParam(value = "id is required to get a user", example = "1")
                                                   @PathVariable("idUser") Long idUser) {
        idValidator.validateUserId(idUser);
        var user = userRepository.getById(idUser);

        return ResponseEntity.ok(user.getSubscriberList());
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Deleting a user's followers", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/{idUser}/subscriber/{idSubscriber}", produces = "application/json")
    private void deleteSubscriberUser(@ApiParam(value = "user id is required to search for the user itself", example = "1")
                                                      @PathVariable("idUser") Long idUser,
                                                      @ApiParam(value = "the subscriber ID is required to remove the user's subscriber", example = "2")
                                                      @PathVariable("idSubscriber") Long idSubscriber) {
        idValidator.validateUserId(idUser);
        idValidator.validateUserId(idSubscriber);

        if (idUser.equals(idSubscriber)) {
            throw new InvalidException();
        }

        userService.deleteSubscriberUser(idUser, idSubscriber);
    }
}
