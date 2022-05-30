package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.dto.PostDTO;
import by.tms.dzen.yandexdzenrestc51.entity.*;
import by.tms.dzen.yandexdzenrestc51.exception.ExistsException;
import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.exception.NotFoundException;
import by.tms.dzen.yandexdzenrestc51.mapper.PostMapper;
import by.tms.dzen.yandexdzenrestc51.repository.*;
import by.tms.dzen.yandexdzenrestc51.service.Impl.CategoryService;
import by.tms.dzen.yandexdzenrestc51.service.Impl.CommentService;
import by.tms.dzen.yandexdzenrestc51.service.Impl.PostService;
import by.tms.dzen.yandexdzenrestc51.service.Impl.UserService;
import by.tms.dzen.yandexdzenrestc51.service.TagService;
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
@Api(tags = "User", description = "Operation about user")
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final IdValidator idValidator;
    private final PostMapper postMapper;
    private final PostService postService;
    private final CommentRepository commentRepository;
     private final CommentService commentService;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final CategoryService categoryService;
    private final TagService tagService;

    public AdminController(UserService userService, UserRepository userRepository, PostRepository postRepository,
                           IdValidator idValidator, PostMapper postMapper, PostService postService,
                           CommentRepository commentRepository,
                           CommentService commentService, CategoryRepository categoryRepository,
                           TagRepository tagRepository, CategoryService categoryService, TagService tagService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.idValidator = idValidator;
        this.postMapper = postMapper;
        this.postService = postService;
        this.commentRepository = commentRepository;
        this.commentService = commentService;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @ApiOperation(value = "Get user by user name", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/user/{username}", produces = "application/json")
    public ResponseEntity<User> getUser(@ApiParam(value = "The name that needs to be fetched", example = "username")
                                        @PathVariable("username") String username) {

        if (username == null | userRepository.findByUsername(username).isEmpty()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(userRepository.findByUsername(username).get());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @ApiOperation(value = "Getting all users", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/user/", produces = "application/json")
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.ok(userRepository.findAll());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "405", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    @ApiOperation(value = "Create new user", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PostMapping(value = "/user/", produces = "application/json")
    public ResponseEntity<User> addUser(@ApiParam(value = "Created user object", example = "User")
                                        @Valid @RequestBody User user,
                                        BindingResult bindingResult) {

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
    @ApiOperation(value = "Updated user", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/user/{username}", produces = "application/json")
    public ResponseEntity<User> updateUser(@ApiParam(value = "username that need to be updated", example = "username")
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
        userService.update(user);

        return ResponseEntity.ok(update);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @ApiOperation(value = "Delete user", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/user/{username}", produces = "application/json")
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
            @ApiResponse(responseCode = "404", description = "Tag not found")
    })
    @ApiOperation(value = "Getting a post by user id", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/post/{id}", produces = "application/json")
    public ResponseEntity<Post> getPost(@ApiParam(value = "An id is needed as a result of which a post under the given" +
            " id will be received. for test data use any number instead of id", example = "1")
                                        @PathVariable("id") Long id) {

        idValidator.validatePostId(id);
        Post getPost = postRepository.findById(id).get();

        return ResponseEntity.ok(getPost);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Tag not found")
    })
    @ApiOperation(value = "Getting posts by user id", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/post/{userId}", produces = "application/json")
    public ResponseEntity<List<Post>> getAllPostByUserId(@ApiParam(value = "User ID is required to get all posts " +
            "of this user", example = "1")
                                                         @PathVariable("userId") Long userId) {

        idValidator.validateUserId(userId);

        return ResponseEntity.ok(postRepository.findAllByUserId(userId).get());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Create post", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PostMapping(value = "/post/{userId}", produces = "application/json")
    public ResponseEntity<Post> addPost(@ApiParam(value = "Created post object for user", example = "1")
                                        @PathVariable("userId") Long userId,
                                        @Valid @RequestBody PostDTO postDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        idValidator.validateUserId(userId);

        Post post = postMapper.postDTOToPost(postDto);
        post.setUser(userRepository.findById(userId).get());
        post.setCreateDate(LocalDateTime.now());
        Post save = postRepository.save(post);

        return ResponseEntity.ok(save);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Updated post", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/post/{id}", produces = "application/json")
    public ResponseEntity<Post> updatePost(@ApiParam(value = "Post id is required to change", example = "1")
                                           @PathVariable("id") Long id, @Valid @RequestBody Post post,
                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        idValidator.validatePostId(id);

        post.setId(id);
        Post save = postRepository.save(post);

        return ResponseEntity.ok(save);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @ApiOperation(value = "Delete post", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/post/{id}", produces = "application/json")
    public void deletePost(@ApiParam(value = "An id is needed as a result of which the post under the given id will " +
            "be deleted. for test data use any number instead of id", example = "1")
                           @PathVariable("id") Long id) {

        idValidator.validatePostId(id);
        postService.delete(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Get comment by id comment", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/comment/{userId}/{postId}/{id}", produces = "application/json")
    public ResponseEntity<Comment> getComment(@ApiParam(value = "UserId is required to get a user for this id", example = "1")
                                              @PathVariable("userId") Long userId,
                                              @ApiParam(value = "Post id is required to get a post for this id", example = "1")
                                              @PathVariable("postId") Long postId,
                                              @ApiParam(value = "Id is required to receive a comment on this id", example = "1")
                                              @PathVariable("id") Long id) {

        idValidator.validateId(id);
        idValidator.validateId(userId);
        idValidator.validateId(postId);
        idValidator.validateUserId(userId);
        idValidator.validatePostId(postId);

        if (commentRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }

        return ResponseEntity.ok(commentRepository.findById(id).get());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Getting all comments on a post with id", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/comment/{postId}", produces = "application/json")
    public ResponseEntity<List<Comment>> getAllCommentByPost(@ApiParam(value = "Post id is required to get all comments on this post", example = "1")
                                                             @PathVariable("postId") Long postId) {

        idValidator.validateId(postId);

        if (postRepository.findById(postId).isEmpty()) {
            throw new NotFoundException();
        }

        return ResponseEntity.ok(commentRepository.findAllByPostId(postId).get());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "405", description = "Invalid input"),
    })
    @ApiOperation(value = "Create a new comment", authorizations = {@Authorization(value = "apiKey")})
    @PostMapping(value = "/comment/{userId}/{postId}", produces = "application/json")
    public ResponseEntity<Comment> addComment(@ApiParam(value = "UserId is required to get a user for this id", example = "1")
                                              @PathVariable("userId") Long userId,
                                              @ApiParam(value = "Post id is required to get a post for this id", example = "1")
                                              @PathVariable("postId") Long postId,
                                              @ApiParam(value = "Creating a comment object", example = "Comment")
                                              @Valid @RequestBody Comment comment,
                                              BindingResult bindingResult) {

        idValidator.validateId(userId);
        idValidator.validateId(postId);
        idValidator.validateUserId(userId);
        idValidator.validatePostId(postId);

        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        comment.setCreateDate(LocalDateTime.now());
        comment.setPost(postRepository.getById(postId));
        Comment save = commentRepository.save(comment);

        return ResponseEntity.ok(save);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Updated comment", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/comment/{userId}/{postId}/{id}", produces = "application/json")
    public ResponseEntity<Comment> updateComment(@ApiParam(value = "UserId is required to get a user for this id", example = "1")
                                                 @PathVariable("userId") Long userId,
                                                 @ApiParam(value = "Post id is required to get a post for this id", example = "1")
                                                 @PathVariable("postId") Long postId,
                                                 @ApiParam(value = "Id is required to receive a comment on this id", example = "1")
                                                 @PathVariable("id") Long id,
                                                 @ApiParam(value = "Creating a modified comment object", example = "Comment")
                                                 @RequestBody Comment comment) {

        idValidator.validateId(id);
        idValidator.validateId(userId);
        idValidator.validateId(postId);
        idValidator.validateUserId(userId);
        idValidator.validatePostId(postId);

        if (commentRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }

        comment.setId(id);
        Comment save = commentRepository.save(comment);

        return ResponseEntity.ok(save);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Deleting a comment", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/comment/{userId}/{postId}/{id}", produces = "application/json")
    public void deleteComment(@ApiParam(value = "UserId is required to get a user for this id", example = "1")
                              @PathVariable("userId") Long userId,
                              @ApiParam(value = "Post id is required to get a post for this id", example = "1")
                              @PathVariable("postId") Long postId,
                              @ApiParam(value = "Id is required to receive a comment on this id", example = "1")
                              @PathVariable("id") Long id) {


        validate(id, userId, postId, id);
        commentService.delete(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Get category by ID", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/category/{id}", produces = "application/json")
    public ResponseEntity<Category> getCategory(@ApiParam(value = "Get category by ID", example = "1")
                                                @PathVariable("id") Long id) {

        idValidator.validateCategoryId(id);

        return ResponseEntity.ok(categoryRepository.findById(id).get());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @ApiOperation(value = "Get all categories", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/category/", produces = "application/json")
    public ResponseEntity<List<Category>> getAllCategory() {

        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "405", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Already exists")
    })
    @ApiOperation(value = "Create category", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PostMapping(value = "/category/", produces = "application/json")
    public ResponseEntity<Category> addCategory(@Valid @ApiParam(value = "Create category object", example = "Category")
                                                @RequestBody Category category,
                                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new ExistsException();
        }

        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Update category by ID", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/category/{id}", produces = "application/json")
    public ResponseEntity<Category> updateCategory(@ApiParam(value = "Update category by ID", example = "1")
                                                   @PathVariable("id") Long id,
                                                   @Valid @RequestBody Category category,
                                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        idValidator.validateCategoryId(id);

        category.setId(id);
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Delete category by ID", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/category/{id}", produces = "application/json")
    public void deleteCategory(@ApiParam(value = "Delete category by ID", example = "1")
                               @PathVariable("id") Long id) {

        idValidator.validateCategoryId(id);
        categoryService.delete(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Tag not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Save tag", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PostMapping(value = "/tag/", produces = "application/json")
    public ResponseEntity<Tag> addTag(@ApiParam(value = "Create new tag object", example = "Tag")
                                      @Valid @RequestBody Tag tag, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        if (tagRepository.findByName(tag.getName()).isPresent()) {
            throw new NotFoundException();
        }

        Tag saveTag = tagRepository.save(tag);

        return ResponseEntity.ok(saveTag);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Tag not found")
    })
    @ApiOperation(value = "Delete tag", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/tag/{id}", produces = "application/json")
    public void deleteTag(@ApiParam(value = "tag removal", example = "1")
                          @PathVariable("id") Long id) {

        idValidator.validateTagId(id);
        tagService.delete(id);
    }

    private void validate(long userId, long postId) {
        idValidator.validateUserId(userId);
        idValidator.validatePostId(postId);
    }
    private void validate(long id, long userId, long postId) {
        idValidator.validateId(id);
        validate(userId, postId);
    }

    private void validate(long id, long userId, long postId, long commendId) {
        validate(id, userId, postId);
        idValidator.validateCommentId(commendId);
    }
}
