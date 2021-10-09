package me.amandaam.lab.api.comment;

import lombok.Builder;
import lombok.Getter;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
public class CommentDTO implements Serializable {
    private Long id;
    private String content;

    public static CommentDTO convertToCommentDTO(Comment d) {
        return CommentDTO.builder().id(d.getId()).content(d.getContent()).build();
    }

    public static List<CommentDTO> convertToCommentDTO(List<Comment> comments) {
        List<CommentDTO> commentsDTO = new LinkedList<CommentDTO>();
        for (Comment c : comments){
            commentsDTO.add(CommentDTO.convertToCommentDTO(c));
        }
        return commentsDTO;
    }


}
