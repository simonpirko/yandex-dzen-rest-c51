package by.tms.dzen.yandexdzenrestc51.Entity;


import by.tms.dzen.yandexdzenrestc51.enums.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.internal.Cascade;

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
    private long countLike;
    @NotNull
    private long countDizlike;
    @NotNull
    private PostType postType;

//    @OneToMany
//    private List<Tag> tagList;
//
//    @OneToMany
//    private List<Сomment> commentList;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;
}
