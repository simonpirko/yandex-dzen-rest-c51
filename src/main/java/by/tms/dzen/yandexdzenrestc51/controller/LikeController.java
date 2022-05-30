package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.entity.Like;
import by.tms.dzen.yandexdzenrestc51.service.LikeService;
import by.tms.dzen.yandexdzenrestc51.validator.IdValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Like", description = "Operations with Like")
@RequestMapping("/api/v1/like")
public class LikeController {
    private final IdValidator idValidator;
    private final LikeService likeService;

    public LikeController(LikeService likeService, IdValidator idValidator) {
        this.likeService = likeService;
        this.idValidator = idValidator;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "405", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Already exists")
    })
    @ApiOperation(value = "Add Like", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @PostMapping(value = "/{userId}/{postId}", produces = "application/json")
    public ResponseEntity<Like> save(@ApiParam(value = "The user who added the like", example = "1")
                                     @PathVariable("userId") Long userId,
                                     @ApiParam(value = "Add like for the post", example = "1")
                                     @PathVariable("postId") Long postId) {

        validate(userId, postId);

        return ResponseEntity.ok(likeService.addLike(userId, postId));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Delete Like", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping("/{userId}/{postId}")
    public void delete(@ApiParam(value = "The user who deleted the like", example = "1")
                       @PathVariable("userId") Long userId,
                       @ApiParam(value = "Remove the like from the post", example = "1")
                       @PathVariable("postId") Long postId) {

        validate(userId, postId);
        likeService.removeLike(userId, postId);
    }

    private void validate(long id, long userId, long postId) {
        idValidator.validateID(id);
        validate(userId, postId);
    }

    private void validate(long userId, long postId) {
        idValidator.validateUserID(userId);
        idValidator.validatePostID(postId);
    }
}
