package by.tms.dzen.yandexdzenrestc51.service.Impl;

import by.tms.dzen.yandexdzenrestc51.entity.Post;
import by.tms.dzen.yandexdzenrestc51.entity.Status;
import by.tms.dzen.yandexdzenrestc51.repository.PostRepository;
import by.tms.dzen.yandexdzenrestc51.service.Crud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class PostService implements Crud<Post> {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    @Override
    public Post save(Post post) {
        post.setCreateDate(LocalDateTime.now());
        Post save = postRepository.save(post);

        return save;
    }

    @Override
    public void delete(Post post) {
        post.setStatus(Status.DELETED);
        postRepository.save(post);

        log.info("Post with id {} was deleted", post.getId());
    }

    @Override
    public void delete(Long id) {
        Post byId = postRepository.getById(id);
        byId.setStatus(Status.DELETED);
        postRepository.save(byId);

        log.info("Post with id {} was deleted", id);
    }

    @Override
    public Post update(Post post) {
        Post save = postRepository.save(post);
        return save;
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow((() -> new RuntimeException("Post with id " + id + " not found")));
    }

    public List<Post> findAllByUserId(Long id) {
        return postRepository.findAllByUserId(id).orElseThrow((() -> new RuntimeException("Posts with user id " + id + " not found")));
    }

}
