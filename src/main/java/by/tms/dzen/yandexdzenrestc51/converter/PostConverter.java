package by.tms.dzen.yandexdzenrestc51.converter;

import by.tms.dzen.yandexdzenrestc51.dto.PostDto;
import by.tms.dzen.yandexdzenrestc51.entity.Post;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostConverter {
    public Post postDtoToPost(PostDto postDto){
        return Post.builder()
                .title(postDto.getTitle())
                .createDate(LocalDateTime.now())
                .numberOfReads(0)
                .countLike(0)
                .countDizlike(0)
                .postType(postDto.getPostType())
                .contents(postDto.getContents())
                .user(postDto.getUser())
                .build();
    }
}