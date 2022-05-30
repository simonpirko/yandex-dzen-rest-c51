package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.entity.DisLike;
import by.tms.dzen.yandexdzenrestc51.service.DisLikeService;
import by.tms.dzen.yandexdzenrestc51.validator.IdValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "DisLike", description = "Operations with DisLike")
@RequestMapping("/api/v1/dislike")
public class DisLikeController {
    @Autowired
    private IdValidator idValidator;
    private final DisLikeService disLikeService;

    public DisLikeController(DisLikeService disLikeService) {
        this.disLikeService = disLikeService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Already exists")
    })
    @ApiOperation(value = "Add DisLike", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @PostMapping("/{userId}/{postId}")
    public ResponseEntity<DisLike> save(@ApiParam(value = "The user who added the dislike", example = "1")
                                        @PathVariable("userId") Long userId,
                                        @ApiParam(value = "Add dislike for the post", example = "1")
                                        @PathVariable("postId") Long postId) {

        idValidator.validateId(userId);
        idValidator.validateId(postId);
        idValidator.validateUserId(userId);
        idValidator.validatePostId(postId);

        return ResponseEntity.ok(disLikeService.addDisLike(userId, postId));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Delete DisLike", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping("/{userId}/{postId}")
    public void delete(@ApiParam(value = "The user who deleted the dislike", example = "1")
                       @PathVariable("userId") Long userId,
                       @ApiParam(value = "Remove the dislike from the post", example = "1")
                       @PathVariable("postId") Long postId) {

        idValidator.validateId(userId);
        idValidator.validateId(postId);
        idValidator.validateUserId(userId);
        idValidator.validatePostId(postId);

        disLikeService.removeDisLike(userId, postId);
    }
}
