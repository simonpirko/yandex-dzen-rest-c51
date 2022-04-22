package by.tms.dzen.yandexdzenrestc51.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 3, max = 25)
    private String username;

    private String password;

    @Size(min = 2, max = 25)
    private String firstName;

    @Size(min = 2, max = 25)
    private String lastName;

    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Pattern(regexp = "^(\\+)?(\\(\\d{2,3}\\) ?\\d|\\d)(([ \\-]?\\d)|( ?\\(\\d{2,3}\\) ?)){5,12}\\d$")
    private String phone;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Role> roleList;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;
}
