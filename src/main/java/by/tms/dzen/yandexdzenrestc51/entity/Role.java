package by.tms.dzen.yandexdzenrestc51.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "ROLES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String typeOfRole;

    public Role(String typeOfRole) {
        this.typeOfRole = typeOfRole;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", typeOfRole='" + typeOfRole + '\'' +
                '}';
    }
}
