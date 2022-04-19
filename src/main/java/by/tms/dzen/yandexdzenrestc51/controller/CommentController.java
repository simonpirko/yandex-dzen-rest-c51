package by.tms.dzen.yandexdzenrestc51.controller;


import by.tms.dzen.yandexdzenrestc51.entity.Comment;
import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.exception.NotFoundException;
import by.tms.dzen.yandexdzenrestc51.repository.CommentRepository;
import by.tms.dzen.yandexdzenrestc51.repository.PostRepository;
import by.tms.dzen.yandexdzenrestc51.validator.LikeValidator;
import io.swagger.annotations.Api;
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

    @PostMapping(value = "/{userId}/{postId}", produces = "application/json")
    public ResponseEntity<Comment> save(@PathVariable("userId") Long userId,
                                        @PathVariable("postId") Long postId,
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

    @GetMapping(value = "/{userId}/{postId}/{id}", produces = "application/json")
    public ResponseEntity<Comment> getComment(@PathVariable("userId") Long userId,
                                              @PathVariable("postId") Long postId,
                                              @PathVariable("id") Long id) {
        likeValidator.validateID(userId, postId);
        likeValidator.existsByUserIdAndPostId(userId, postId);
        if (id < 0 | commentRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        Comment comment = commentRepository.findById(id).get();

        return ResponseEntity.ok(comment);
    }

    @GetMapping(value = "/{postId}", produces = "application/json")
    public ResponseEntity<List<Comment>> getAllCommentByPost(@PathVariable("postId") Long postId) {
        if (postId < 0 | postRepository.findById(postId).isEmpty()) {
            throw new NotFoundException();
        }
        List<Comment> commentList = commentRepository.findAllByPostId(postId).get();

        return ResponseEntity.ok(commentList);
    }

    @PutMapping(value = "/{userId}/{postId}/{id}", produces = "application/json")
    public ResponseEntity<Comment> updateComment(@PathVariable("userId") Long userId,
                                                 @PathVariable("postId") Long postId,
                                                 @PathVariable("id") Long id,
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

    @DeleteMapping(value = "/{userId}/{postId}/{id}", produces = "application/json")
    public void save(@PathVariable("userId") Long userId,
                                        @PathVariable("postId") Long postId,
                                        @PathVariable("id") Long id) {
        likeValidator.validateID(userId, postId);
        likeValidator.existsByUserIdAndPostId(userId, postId);
        if (id < 0 | commentRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        commentRepository.delete(commentRepository.getById(id));
    }
}
