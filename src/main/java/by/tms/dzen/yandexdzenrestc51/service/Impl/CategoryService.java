package by.tms.dzen.yandexdzenrestc51.service.Impl;

import by.tms.dzen.yandexdzenrestc51.entity.Category;
import by.tms.dzen.yandexdzenrestc51.entity.Status;
import by.tms.dzen.yandexdzenrestc51.exception.ExistsException;
import by.tms.dzen.yandexdzenrestc51.exception.NotFoundException;
import by.tms.dzen.yandexdzenrestc51.repository.CategoryRepository;
import by.tms.dzen.yandexdzenrestc51.service.Crud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CategoryService implements Crud<Category> {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void delete(Long id) {
        Category byId = categoryRepository.getById(id);
        byId.setStatus(Status.DELETED);
        categoryRepository.save(byId);

        log.info("IN delete - category: {} successfully deleted", byId);
    }

    @Override
    public Category save(Category category) {
        categoryRepository.findByName(category.getName()).ifPresent(c -> {
            throw new ExistsException("Category with name: " + category.getName() + " already exists");
        });

        log.info("IN save - category: {} successfully saved", category);
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Category category) {
        categoryRepository.findById(category.getId()).ifPresent(c -> {
            c.setStatus(Status.DELETED);
            categoryRepository.save(c);

            log.info("IN delete - category: {} successfully deleted", c);
        });

        throw new NotFoundException("Category with id: " + category.getId() + " not found");
    }

    @Override
    public Category update(Category category) {
        categoryRepository.findById(category.getId()).orElseThrow(() -> new NotFoundException("Category with id: " + category.getId() + " not found"));
        Category updated = categoryRepository.save(category);
        log.info("IN update - category: {} successfully updated", updated);

        return updated;
    }

    @Override
    public Category findById(Long id) {
        categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category with id: " + id + " not found"));

        return categoryRepository.getById(id);
    }
}
