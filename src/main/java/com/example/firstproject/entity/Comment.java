package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
    @Column
    private String nickname;
    @Column
    private String body;

    public static Comment createComment(CommentDto dto, Article article) {
        //예외1 : 이미 댓글dto에 id가 있음
        if (dto.getId() != null) throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다");
        //예외2 : 댓글dto의 게시글 id와 article 엔티티의 id가 다름
        if (!Objects.equals(dto.getArticleId(), article.getId())) throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었습니다");
        return new Comment(
                dto.getId(),article,dto.getNickname(),dto.getBody()
        );
    }

    public void patch(CommentDto dto) {
        if (this.id != dto.getId())
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력됐습니다.");
        if (dto.getNickname() != null) this.nickname = dto.getNickname();
        if (dto.getBody() != null) this.body = dto.getBody();
    }
}
