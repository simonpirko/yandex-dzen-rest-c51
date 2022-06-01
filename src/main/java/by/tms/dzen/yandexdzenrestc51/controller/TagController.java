package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.entity.Tag;
import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.exception.NotFoundException;
import by.tms.dzen.yandexdzenrestc51.repository.TagRepository;
import by.tms.dzen.yandexdzenrestc51.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@PreAuthorize("hasRole('USER')")

@Slf4j
@RestController
@Api(tags = "Tag", description = "Access to tag")
@RequestMapping("/api/v1/user/tag")
public class TagController {
    private final TagService tagService;

    private final TagRepository tagRepository;

    public TagController(TagRepository tagRepository, TagService tagService) {
        this.tagRepository = tagRepository;
        this.tagService = tagService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Tag not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Save tag", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @PostMapping
    public ResponseEntity<Tag> save(@ApiParam(value = "Create new tag object", example = "Tag")
                                         @Valid @RequestBody Tag tag, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        if (tagRepository.findByName(tag.getName()).isPresent()) {
            throw new NotFoundException();
        }

        Tag saveTag = tagRepository.save(tag);

        log.info("New tag with name {} added", tag.getName());

        return ResponseEntity.ok(saveTag);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Tag not found")
    })
    @ApiOperation(value = "Delete tag", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(produces = "application/json")
    public void delete(@Valid @RequestBody Tag tag, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        if (tagRepository.findByName(tag.getName()).isEmpty()) {
            throw new NotFoundException();
        }

        tagService.deleteById(tag.getId());
    }
}
