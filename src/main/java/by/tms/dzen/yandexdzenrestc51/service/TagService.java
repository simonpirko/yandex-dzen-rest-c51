package by.tms.dzen.yandexdzenrestc51.service;

import by.tms.dzen.yandexdzenrestc51.entity.Status;
import by.tms.dzen.yandexdzenrestc51.entity.Tag;
import by.tms.dzen.yandexdzenrestc51.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public void deleteById(Long id) {
        Tag byId = tagRepository.getById(id);
        byId.setStatus(Status.DELETED);
        tagRepository.save(byId);

        log.info("IN delete - tag: {} successfully deleted", byId);
    }

    public void deleteByName(String name) {
        Tag byName = tagRepository.findByName(name).get();
        byName.setStatus(Status.DELETED);
        tagRepository.save(byName);

        log.info("IN delete - tag: {} successfully deleted", byName);
    }
}
