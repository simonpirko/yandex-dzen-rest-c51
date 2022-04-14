package by.tms.dzen.yandexdzenrestc51.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
//@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Сomments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime createDate;
    private long countLike;
    private long countDizlike;


}
