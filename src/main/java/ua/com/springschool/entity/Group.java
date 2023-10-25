package ua.com.springschool.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Student> students;
}
