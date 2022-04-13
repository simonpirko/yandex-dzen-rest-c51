package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.Entity.Post;
import by.tms.dzen.yandexdzenrestc51.Entity.User;
import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.exception.UserNotFoundException;
import by.tms.dzen.yandexdzenrestc51.repository.PostRepository;
import by.tms.dzen.yandexdzenrestc51.repository.UserRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

    @PostMapping
    public ResponseEntity<Post> save(@Valid @RequestBody Post post, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new InvalidException();
        }
        Post save = postRepository.save(post);
        return ResponseEntity.ok(save);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable("id") Long id){
        if (id < 1 | postRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException();
        }
        Post getPost = postRepository.findById(id).get();
        return ResponseEntity.ok(getPost);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        if (id < 1 | postRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException();
        }
        Post post = postRepository.findById(id).get();
        postRepository.delete(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable("id") Long id, Post post){
        if (id < 1 | postRepository.findById(id).isEmpty()){
            throw new UserNotFoundException();
        }
        post.setId(id);
        Post save = postRepository.save(post);
        return ResponseEntity.ok(save);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getAllPostByUserId(@PathVariable("{userId}") Long userId){
        if (userId < 1 | userRepository.findById(userId).isEmpty()){
            throw new UserNotFoundException();
        }
        List<Post> postLis = postRepository.findAllByUserId(userId).get();
        return ResponseEntity.ok(postLis);
    }
}
