package by.tms.dzen.yandexdzenrestc51.service;

import by.tms.dzen.yandexdzenrestc51.entity.Category;
import by.tms.dzen.yandexdzenrestc51.entity.Status;
import by.tms.dzen.yandexdzenrestc51.repository.CategoryRepository;
import org.springframework.stereotype.Component;

@Component
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void delete(Long id) {
        Category byId = categoryRepository.getById(id);
        byId.setStatus(Status.DELETED);
        categoryRepository.save(byId);
    }
}
