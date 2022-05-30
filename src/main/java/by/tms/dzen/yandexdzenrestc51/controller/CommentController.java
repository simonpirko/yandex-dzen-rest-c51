package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.entity.Comment;
import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.repository.PostRepository;
import by.tms.dzen.yandexdzenrestc51.service.Impl.CommentService;
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
@Api(tags = "Comment", description = "Operations with comments")
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentService commentService;
    private final IdValidator idValidator;
    private final PostRepository postRepository;

    public CommentController(PostRepository postRepository, IdValidator idValidator, CommentService commentService) {
        this.postRepository = postRepository;
        this.idValidator = idValidator;
        this.commentService = commentService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "405", description = "Invalid input"),
    })
    @ApiOperation(value = "Create a new comment", authorizations = {@Authorization(value = "apiKey")})
    @PostMapping(value = "/{userId}/{postId}", produces = "application/json")
    public ResponseEntity<Comment> save(@ApiParam(value = "UserId is required to get a user for this id", example = "1")
                                        @PathVariable("userId") Long userId,
                                        @ApiParam(value = "Post id is required to get a post for this id", example = "1")
                                        @PathVariable("postId") Long postId,
                                        @ApiParam(value = "Creating a comment object", example = "Comment")
                                        @Valid @RequestBody Comment comment,
                                        BindingResult bindingResult) {

        validate(userId, postId);

        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        comment.setCreateDate(LocalDateTime.now());
        comment.setPost(postRepository.getById(postId));

        return ResponseEntity.ok(commentService.save(comment));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Get comment by id comment", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/{userId}/{postId}/{id}", produces = "application/json")
    public ResponseEntity<Comment> getComment(@ApiParam(value = "UserId is required to get a user for this id", example = "1")
                                              @PathVariable("userId") Long userId,
                                              @ApiParam(value = "Post id is required to get a post for this id", example = "1")
                                              @PathVariable("postId") Long postId,
                                              @ApiParam(value = "Id is required to receive a comment on this id", example = "1")
                                              @PathVariable("id") Long id) {

        validate(id, userId, postId);

        return ResponseEntity.ok(commentService.findById(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Getting all comments on a post with id", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/{postId}", produces = "application/json")
    public ResponseEntity<List<Comment>> getAllCommentByPost(@ApiParam(value = "Post id is required to get all comments on this post", example = "1")
                                                             @PathVariable("postId") Long postId) {

        idValidator.validateId(postId);
        return ResponseEntity.ok(commentService.findAllByPostId(postId));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Updated comment", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/{userId}/{postId}/{id}", produces = "application/json")
    public ResponseEntity<Comment> updateComment(@ApiParam(value = "UserId is required to get a user for this id", example = "1")
                                                 @PathVariable("userId") Long userId,
                                                 @ApiParam(value = "Post id is required to get a post for this id", example = "1")
                                                 @PathVariable("postId") Long postId,
                                                 @ApiParam(value = "Id is required to receive a comment on this id", example = "1")
                                                 @PathVariable("id") Long id,
                                                 @ApiParam(value = "Creating a modified comment object", example = "Comment")
                                                 @RequestBody Comment comment) {

        validate(id, userId, postId);
        comment.setId(id);

        return ResponseEntity.ok(commentService.save(comment));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Deleting a comment", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/{userId}/{postId}/{id}", produces = "application/json")
    public void deleteComment(@ApiParam(value = "UserId is required to get a user for this id", example = "1")
                              @PathVariable("userId") Long userId,
                              @ApiParam(value = "Post id is required to get a post for this id", example = "1")
                              @PathVariable("postId") Long postId,
                              @ApiParam(value = "Id is required to receive a comment on this id", example = "1")
                              @PathVariable("id") Long id) {

        validate(id, userId, postId);
        commentService.delete(id);
    }

    private void validate(long id, long userId, long postId) {
        idValidator.validateId(id);
        validate(userId, postId);
    }

    private void validate(long userId, long postId) {
        idValidator.validateUserId(userId);
        idValidator.validatePostId(postId);
    }
}
