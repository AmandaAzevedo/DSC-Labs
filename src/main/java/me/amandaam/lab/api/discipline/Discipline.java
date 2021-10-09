package me.amandaam.lab.api.discipline;

import lombok.*;
import me.amandaam.lab.api.comment.Comment;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
@Entity
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discipline_generator")
    @SequenceGenerator(name="discipline_generator",sequenceName = "discipline_sequence", allocationSize = 1)
    private Long id;
    private String name;
    private Long likes;
    private Double note;
    @OneToMany(mappedBy = "discipline")
    private List<Comment> comments;

}