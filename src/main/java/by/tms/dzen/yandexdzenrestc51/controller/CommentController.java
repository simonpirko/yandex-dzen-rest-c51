package by.tms.dzen.yandexdzenrestc51.controller;


import by.tms.dzen.yandexdzenrestc51.entity.Comment;
import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.exception.NotFoundException;
import by.tms.dzen.yandexdzenrestc51.repository.CommentRepository;
import by.tms.dzen.yandexdzenrestc51.repository.PostRepository;
import by.tms.dzen.yandexdzenrestc51.validator.LikeValidator;
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
    private final LikeValidator likeValidator;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentController(LikeValidator likeValidator, CommentRepository commentRepository, PostRepository postRepository) {
        this.likeValidator = likeValidator;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "405", description = "Invalid input"),
    })
    @ApiOperation(value = "Create a new comment", authorizations = {@Authorization(value = "apiKey")})
    @PostMapping(value = "/{userId}/{postId}", produces = "application/json")
    public ResponseEntity<Comment> save(@ApiParam(value = "UserId is required to get a user for this id", example = "test1")
                                        @PathVariable("userId") Long userId,
                                        @ApiParam(value = "Post id is required to get a post for this id", example = "1")
                                        @PathVariable("postId") Long postId,
                                        @ApiParam(value = "Creating a comment object", name = "body comment")
                                        @Valid @RequestBody Comment comment, BindingResult bindingResult) {
        likeValidator.validateID(userId, postId);
        likeValidator.existsByUserIdAndPostId(userId, postId);
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
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Get comment by id comment", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/{userId}/{postId}/{id}", produces = "application/json")
    public ResponseEntity<Comment> getComment(@ApiParam(value = "UserId is required to get a user for this id", example = "test1")
                                              @PathVariable("userId") Long userId,
                                              @ApiParam(value = "Post id is required to get a post for this id", example = "1")
                                              @PathVariable("postId") Long postId,
                                              @ApiParam(value = "Id is required to receive a comment on this id", example = "1")
                                              @PathVariable("id") Long id) {
        likeValidator.validateID(userId, postId);
        likeValidator.existsByUserIdAndPostId(userId, postId);
        if (id < 0 | commentRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        Comment comment = commentRepository.findById(id).get();

        return ResponseEntity.ok(comment);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Getting all comments on a post with id", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/{postId}", produces = "application/json")
    public ResponseEntity<List<Comment>> getAllCommentByPost(@ApiParam(value = "Post id is required to get all comments on this post", example = "1")
                                                             @PathVariable("postId") Long postId) {
        if (postId < 0 | postRepository.findById(postId).isEmpty()) {
            throw new NotFoundException();
        }
        List<Comment> commentList = commentRepository.findAllByPostId(postId).get();

        return ResponseEntity.ok(commentList);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Updated comment", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/{userId}/{postId}/{id}", produces = "application/json")
    public ResponseEntity<Comment> updateComment(@ApiParam(value = "UserId is required to get a user for this id", example = "test1")
                                                 @PathVariable("userId") Long userId,
                                                 @ApiParam(value = "Post id is required to get a post for this id", example = "1")
                                                 @PathVariable("postId") Long postId,
                                                 @ApiParam(value = "Id is required to receive a comment on this id", example = "1")
                                                 @PathVariable("id") Long id,
                                                 @ApiParam(value = "Creating a modified comment object", name = "body comment")
                                                 @RequestBody Comment comment) {
        likeValidator.validateID(userId, postId);
        likeValidator.existsByUserIdAndPostId(userId, postId);
        if (id < 0 | commentRepository.findById(id).isEmpty()) {
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
    @ApiOperation(value = "Deleting a comment", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/{userId}/{postId}/{id}", produces = "application/json")
    public void deleteComment(@ApiParam(value = "UserId is required to get a user for this id", example = "test1")
                              @PathVariable("userId") Long userId,
                              @ApiParam(value = "Post id is required to get a post for this id", example = "1")
                              @PathVariable("postId") Long postId,
                              @ApiParam(value = "Id is required to receive a comment on this id", example = "1")
                              @PathVariable("id") Long id) {
        likeValidator.validateID(userId, postId);
        likeValidator.existsByUserIdAndPostId(userId, postId);
        if (id < 0 | commentRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        commentRepository.delete(commentRepository.getById(id));
    }
}
