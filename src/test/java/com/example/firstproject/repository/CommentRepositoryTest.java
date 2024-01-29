package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;
    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleId() {
        //1.입력 데이터 준비
        Long articleId = 4L;
        //2.실제 데이터
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        //3.예상 데이터
        Article article = new Article(4L,"당신의 인생 영화는?","댓글 고");
        Comment a = new Comment(1L,article,"P","영화1");
        Comment b = new Comment(2L,article,"K","영화2");
        Comment c = new Comment(3L,article,"C","영화3");
        List<Comment> expected = Arrays.asList(a,b,c);
        //4.비교 및 검증
        assertEquals(expected.toString(),comments.toString(),"모든 메시지 출력!");
    }
    @Test
    void findByNickname() {

    }
}