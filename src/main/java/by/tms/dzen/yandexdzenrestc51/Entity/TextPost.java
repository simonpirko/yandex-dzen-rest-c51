package by.tms.dzen.yandexdzenrestc51.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TextPost extends Post{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
