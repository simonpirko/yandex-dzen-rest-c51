package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.converter.PostConverter;
import by.tms.dzen.yandexdzenrestc51.dto.PostDto;
import by.tms.dzen.yandexdzenrestc51.entity.Post;
import by.tms.dzen.yandexdzenrestc51.exception.UserNotFoundException;
import by.tms.dzen.yandexdzenrestc51.repository.PostRepository;
import by.tms.dzen.yandexdzenrestc51.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostConverter postConverter;

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable("id") Long id) {
        if (id < 1 | postRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException();
        }
        Post getPost = postRepository.findById(id).get();
        return ResponseEntity.ok(getPost);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getAllPostByUserId(@PathVariable("userId") Long userId) {
        if (userId < 1 | userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException();
        }
        List<Post> postLis = postRepository.findAllByUserId(userId).get();
        return ResponseEntity.ok(postLis);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        if (id < 1 | postRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException();
        }
        Post post = postRepository.findById(id).get();
        postRepository.delete(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable("id") Long id, Post post) {
        if (id < 1 | postRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException();
        }
        post.setId(id);
        Post save = postRepository.save(post);
        return ResponseEntity.ok(save);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Post> create(@PathVariable("userId") Long userId, @Valid @RequestBody PostDto postDto, BindingResult bindingResult) {
        if (userId < 1 | bindingResult.hasErrors() | userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException();
        }
        Post post = postConverter.postDtoToPost(postDto);
        post.setUser(userRepository.findById(userId).get());
        Post save = postRepository.save(post);
        return ResponseEntity.ok(save);
    }

}
