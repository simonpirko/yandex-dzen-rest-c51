package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.entity.Like;
import by.tms.dzen.yandexdzenrestc51.exception.NotFoundException;
import by.tms.dzen.yandexdzenrestc51.service.LikeDisLikeService;
import by.tms.dzen.yandexdzenrestc51.validator.LikeValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Like", description = "Operations with Like")
@RequestMapping("/api/v1/like")
public class LikeController {
    private final LikeDisLikeService likeDisLikeService;
    private final LikeValidator likeValidator;

    public LikeController(LikeDisLikeService likeDisLikeService, LikeValidator likeValidator) {
        this.likeDisLikeService = likeDisLikeService;
        this.likeValidator = likeValidator;
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Like not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Add like", notes = "This can only be done by the logged in user")
    @PostMapping(value = "/{userId}/{postId}", produces = "application/json")
    public ResponseEntity<Like> save(@PathVariable("userId") @ApiParam(value = "The user who added the like", example = "userId") Long userId,
                                     @PathVariable("postId") @ApiParam(value = "Add like for the post", example = "postId") Long postId) {
        likeValidator.validateID(userId, postId);
        likeValidator.existsByUserIdAndPostId(userId, postId);

        return ResponseEntity.ok(likeDisLikeService.addLike(userId, postId));
    }

    @DeleteMapping("/{userId}/{postId}")
    public void delete(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId) {
        likeValidator.validateID(userId, postId);

        if (likeValidator.existsByUserIdAndPostId(userId, postId)) {
            likeDisLikeService.removeLike(userId, postId);
        } else {
            throw new NotFoundException("Like not found");
        }
    }
}
