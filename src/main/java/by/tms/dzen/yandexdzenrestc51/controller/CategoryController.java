package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.entity.Category;
import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.repository.CategoryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "Category", description = "Operations with category")
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Create category", notes = "This can only be done by the logged in user")
    @PostMapping(produces = "application/json")
    public ResponseEntity<Category> save(@Valid @RequestBody
                                         @ApiParam(value = "Create category object") Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors() | categoryRepository.findByName(category.getName()).isPresent()) {
            throw new InvalidException();
        }

        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
    })
    @ApiOperation(value = "Get all categories", notes = "This can only be done by the logged in user")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Get category by ID", notes = "This can only be done by the logged in user")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Category> getById(@PathVariable("id") Long id) {
        if (id < 1 | categoryRepository.findById(id).isEmpty()) {
            throw new InvalidException();
        }

        Category category = categoryRepository.findById(id).get();
        return ResponseEntity.ok(category);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Delete category by ID", notes = "This can only be done by the logged in user")
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void delete(@PathVariable("id") Long id) {
        if (id < 1 | categoryRepository.findById(id).isEmpty()) {
            throw new InvalidException();
        }

        categoryRepository.deleteById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Update category by ID", notes = "This can only be done by the logged in user")
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Category> update(@PathVariable("id") Long id,
                                           @Valid @RequestBody Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors() | categoryRepository.findById(id).isEmpty()) {
            throw new InvalidException();
        }

        category.setId(id);
        return ResponseEntity.ok(categoryRepository.save(category));
    }
}

