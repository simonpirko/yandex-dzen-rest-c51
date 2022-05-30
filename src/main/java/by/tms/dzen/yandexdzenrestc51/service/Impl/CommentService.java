package by.tms.dzen.yandexdzenrestc51.service.Impl;

import by.tms.dzen.yandexdzenrestc51.entity.Comment;
import by.tms.dzen.yandexdzenrestc51.entity.Status;
import by.tms.dzen.yandexdzenrestc51.exception.NotFoundException;
import by.tms.dzen.yandexdzenrestc51.repository.CommentRepository;
import by.tms.dzen.yandexdzenrestc51.repository.PostRepository;
import by.tms.dzen.yandexdzenrestc51.service.Crud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CommentService implements Crud<Comment> {
    @Autowired
    private PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        comment.setStatus(Status.DELETED);
        commentRepository.save(comment);

        log.info("IN delete - comment: {} successfully deleted", comment.getId());
    }

    @Override
    public Comment update(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment with id: " + id + " not found"));
    }

    public List<Comment> findAllByPostId(long postId) {
        return commentRepository.findAllByPostId(postId).orElseThrow(() -> new NotFoundException("Comments with postId: " + postId + " not found"));
    }

    @Override
    public void delete(Long id) {
        Comment byId = commentRepository.getById(id);
        byId.setStatus(Status.DELETED);
        commentRepository.save(byId);

        log.info("IN delete - comment: {} successfully deleted", byId);
    }
}
