package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
//        //1. 댓글 조회
//        List<Comment> comments = commentRepository.findByArticleId(articleId);
//        //2. 엔티티 -> DTO
//        List<CommentDto> dtos = new ArrayList<>();
//        for (int i = 0; i<comments.size(); i++){
//            Comment c = comments.get(i);
//            dtos.add(CommentDto.createCommentDto(c));
//        }
        //3. 결과 반환
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }
    @Transactional //create 메서드는 DB내용을 바꾸기 때문에 실패할 경우를 대비해야함
    public CommentDto create(Long articleId, CommentDto dto) {
        //게시글 id와 댓글 dto를 받음 -> 게시글 엔티티 가져옴 -> 댓글 dto를 댓글 엔티티로
        //1. 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId)
                .orElseThrow(()-> new IllegalArgumentException("댓글 생성 실패!" + "대상 게시글이 없습니다"));
        //2. 댓글dto와 게시글 엔티티 받아서 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto,article);
        //3. 댓글 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment);
        //4. 댓글 엔티티를 다시 dto로 변환해 반환
        return CommentDto.createCommentDto(created);
    }
    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        Comment target = commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("댓글 수정 실패!" + "대상 댓글이 없습니다"));
        target.patch(dto);
        Comment updated = commentRepository.save(target);
        return CommentDto.createCommentDto(updated);
    }
    @Transactional
    public CommentDto delete(Long id) {
        Comment target = commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("댓글 수정 실패!" + "대상 댓글이 없습니다"));
        commentRepository.delete(target);
        return CommentDto.createCommentDto(target);
    }
}
