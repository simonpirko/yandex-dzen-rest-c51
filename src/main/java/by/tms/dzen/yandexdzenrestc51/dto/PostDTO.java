package by.tms.dzen.yandexdzenrestc51.dto;

import by.tms.dzen.yandexdzenrestc51.entity.User;
import by.tms.dzen.yandexdzenrestc51.enums.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    @NotNull
    private String title;

    @NotNull
    private PostType postType;

    @NotNull
    private String contents;
    private User user;
}
