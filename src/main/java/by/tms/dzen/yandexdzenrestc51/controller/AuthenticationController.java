package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.configuration.security.jwt.JWTTokenProvider;
import by.tms.dzen.yandexdzenrestc51.dto.AuthRequestDTO;
import by.tms.dzen.yandexdzenrestc51.dto.UserDTO;
import by.tms.dzen.yandexdzenrestc51.entity.User;
import by.tms.dzen.yandexdzenrestc51.exception.ForbiddenException;
import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.service.Impl.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = "Authentication", description = "Operations with authentication")
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final UserService service;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    public AuthenticationController(UserService service, AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "User authorization")
    @PostMapping(value = "/login")
    public ResponseEntity<Map<Object, Object>> login(@ApiParam(value = "Authorization object", example = "requestDto")
                                                     @Valid @RequestBody AuthRequestDTO requestDto,
                                                     BindingResult result) {

        if (result.hasErrors()) {
            throw new InvalidException("Invalid username or password");
        }

        String username = requestDto.getUsername();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = service.findByUsername(username);
        String token = jwtTokenProvider.generateToken(username, user.getRoleList());

        Map<Object, Object> resp = new HashMap<>();
        resp.put("username", username);
        resp.put("token", token);

        log.info("The user with the name {} is logged into the programs", requestDto.getUsername());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "User registration")
    @PostMapping(value = "/reg")
    public ResponseEntity<UserDTO> registration(@ApiParam(value = "Create a new user object", example = "userDTO")
                                                @Valid @RequestBody UserDTO userDTO,
                                                BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidException();
        }

        service.registration(userDTO);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @ApiOperation(value = "Logout user", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/logout")
    public ResponseEntity<Map<Object, Object>> logOut(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<Object, Object> resp = new HashMap<>();

        if (auth != null) {
            resp.put("username", auth.getName());
            resp.put("session, lastAccessedTime", session.getLastAccessedTime());
            new SecurityContextLogoutHandler().logout(request, response, auth);

            log.info("The user with the name {} has left the program", auth.getName());
        } else {
            throw new ForbiddenException("You are not authorized");
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
