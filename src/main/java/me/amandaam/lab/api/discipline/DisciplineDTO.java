package me.amandaam.lab.api.discipline;

import lombok.Builder;
import lombok.Getter;
import me.amandaam.lab.api.comment.CommentDTO;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
public class DisciplineDTO implements Serializable {
    private Long id;
    private String name;
    private Long likes = 0L;
    private Double note;
    private List<CommentDTO> comments;

    public static DisciplineDTO convertToDisciplineDTO(Discipline d) {
        return DisciplineDTO.builder().id(d.getId()).name(d.getName()).likes(d.getLikes()).note(d.getNote())
                .comments(d.getComments() != null ? CommentDTO.convertToCommentDTO(d.getComments()) : null).build();
    }
}
