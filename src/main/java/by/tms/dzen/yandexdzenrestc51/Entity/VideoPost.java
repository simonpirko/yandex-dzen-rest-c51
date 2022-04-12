package by.tms.dzen.yandexdzenrestc51.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class VideoPost extends Post{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private LocalDateTime createDate;
    private long numberOfReads;
    private long countLike;
    private long countDizlike;

    @OneToMany
    private List<Tag> tagList;
    @OneToMany
    private List<Сomment> commentList;
}
