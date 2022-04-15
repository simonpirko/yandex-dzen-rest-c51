package by.tms.dzen.yandexdzenrestc51.mapper;

import by.tms.dzen.yandexdzenrestc51.dto.PostDTO;
import by.tms.dzen.yandexdzenrestc51.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDTO postToPostDTO(Post post);
    Post postDTOToPost(PostDTO postDTO);
}

