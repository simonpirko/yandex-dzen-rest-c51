package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.Entity.Category;
import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.repository.CategoryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(tags = "Category", description = "Operations with category")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Create category", notes = "This can only be done by the logged in user.")
    @PostMapping(produces = "application/json")
    public ResponseEntity<Category> save(@Valid @RequestBody Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors() | categoryRepository.findByName(category.getName()).isPresent()) {
            throw new InvalidException();
        }

        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @GetMapping(produces = "application/json")
    public Iterable<Category> getAll() {
        return categoryRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Category getById(@PathVariable("id") Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new InvalidException());
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void delete(@PathVariable("id") Long id) {
        categoryRepository.deleteById(id);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Category> update(@PathVariable("id") Long id, @Valid @RequestBody Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors() | !categoryRepository.findById(id).isPresent()) {
            throw new InvalidException();
        }

        category.setId(id);
        return ResponseEntity.ok(categoryRepository.save(category));
    }
}

