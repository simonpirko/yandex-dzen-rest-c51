package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.dto.PostDTO;
import by.tms.dzen.yandexdzenrestc51.entity.Post;
import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.exception.NotFoundException;
import by.tms.dzen.yandexdzenrestc51.mapper.PostMapper;
import by.tms.dzen.yandexdzenrestc51.repository.PostRepository;
import by.tms.dzen.yandexdzenrestc51.repository.UserRepository;
import by.tms.dzen.yandexdzenrestc51.validator.IdValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Api(tags = "Post", description = "Access to posts")
@RequestMapping("/api/v1/post")
public class PostController {
    private final IdValidator idValidator;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    public PostController(PostRepository postRepository, UserRepository userRepository, PostMapper postMapper, IdValidator idValidator) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
        this.idValidator = idValidator;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Tag not found")
    })
    @ApiOperation(value = "Getting a post by user id", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Post> getPost(@ApiParam(value = "An id is needed as a result of which a post under the given" +
            " id will be received. for test data use any number instead of id", example = "1")
                                        @PathVariable("id") Long id) {

        idValidator.validateID(id);

        if (postRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }

        Post getPost = postRepository.findById(id).get();
        return ResponseEntity.ok(getPost);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Tag not found")
    })
    @ApiOperation(value = "Getting posts by user id", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/user/{userId}", produces = "application/json")
    public ResponseEntity<List<Post>> getAllPostByUserId(@ApiParam(value = "User ID is required to get all posts " +
            "of this user", example = "1")
                                                         @PathVariable("userId") Long userId) {

        idValidator.validateID(userId);

        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException();
        }

        List<Post> postLis = postRepository.findAllByUserId(userId).get();
        return ResponseEntity.ok(postLis);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Create post", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @PostMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<Post> createPost(@ApiParam(value = "Created post object for user", example = "1")
                                           @PathVariable("userId") Long userId,
                                           @Valid @RequestBody PostDTO postDto, BindingResult bindingResult) {

        idValidator.validateID(userId);

        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException();
        }

        Post post = postMapper.postDTOToPost(postDto);
        post.setUser(userRepository.findById(userId).get());
        post.setCreateDate(LocalDateTime.now());
        Post save = postRepository.save(post);
        return ResponseEntity.ok(save);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @ApiOperation(value = "Delete post", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deletePost(@ApiParam(value = "An id is needed as a result of which the post under the given id will " +
            "be deleted. for test data use any number instead of id", example = "1")
                           @PathVariable("id") Long id) {

        idValidator.validateID(id);

        if (postRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }

        Post post = postRepository.findById(id).get();
        postRepository.delete(post);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Updated post", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Post> updatePost(@ApiParam(value = "Post id is required to change", example = "1")
                                           @PathVariable("id") Long id, @RequestBody Post post) {

        idValidator.validateID(id);

        if (postRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }

        post.setId(id);
        Post save = postRepository.save(post);
        return ResponseEntity.ok(save);
    }
}
