package com.ftn.redditClone.serviceImpl;

import com.ftn.redditClone.model.entity.Comment;
import com.ftn.redditClone.repository.CommentRepository;
import com.ftn.redditClone.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteById(int id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Optional<Comment> findById(int id) {
        return commentRepository.findById(id);
    }

    @Override
    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> soredList(String sortType, int id) {

        if(sortType.equals("Top")){
            List<Comment> comments = commentRepository.sortTop(id);
            return commentRepository.sortTop(id);
        }else if(sortType.equals("New")){
            return commentRepository.sortNew(id);
        }else {
            return commentRepository.sortOld(id);
        }
    }
}
