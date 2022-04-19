package by.tms.dzen.yandexdzenrestc51.entity;


import by.tms.dzen.yandexdzenrestc51.enums.PostType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "POSTS")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String title;

    @NotNull
    private LocalDateTime createDate;

    @NotNull
    private long numberOfReads;

    @NotNull
    private PostType postType;

    @NotNull
    private String contents;

    @OneToMany
    private List<Tag> tagList;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Like> likes;

    @JsonIgnore
    @OneToMany
    private List<DisLike> dislikes;

    @JsonIgnore
    @OneToMany
    private List<Comment> comments;
}
