package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.converter.PostConverter;
import by.tms.dzen.yandexdzenrestc51.dto.PostDto;
import by.tms.dzen.yandexdzenrestc51.entity.Post;
import by.tms.dzen.yandexdzenrestc51.entity.User;
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

    @Autowired
    private PostConverter postConverter;

    @PostMapping("/{userId}")
    public ResponseEntity<Post> save(@PathVariable("userId") Long userId,
                                     @Valid @RequestBody PostDto postDto, BindingResult bindingResult){
        if (bindingResult.hasErrors() | userId < 1 | userRepository.findById(userId).isEmpty()){
            throw new InvalidException();
        }
        postDto.setUser(userRepository.findById(userId).get());
        Post post = postConverter.postDtoToPost(postDto);
        return ResponseEntity.ok(post);
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
