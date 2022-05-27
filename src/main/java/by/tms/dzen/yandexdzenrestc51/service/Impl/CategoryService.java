package by.tms.dzen.yandexdzenrestc51.service.Impl;

import by.tms.dzen.yandexdzenrestc51.entity.Category;
import by.tms.dzen.yandexdzenrestc51.entity.Status;
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
        return null;
    }

    @Override
    public void delete(Category category) {
        category.setStatus(Status.DELETED);
        categoryRepository.save(category);

        log.info("IN delete - category: {} successfully deleted", category.getId());
    }

    @Override
    public void update(Category category) {

    }

    @Override
    public Category findById(Long id) {
        return null;
    }
}
