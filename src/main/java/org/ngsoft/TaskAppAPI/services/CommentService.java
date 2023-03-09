package org.ngsoft.TaskAppAPI.services;

import org.ngsoft.TaskAppAPI.entities.Comment;
import org.ngsoft.TaskAppAPI.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    public Comment findById(Long id) throws ChangeSetPersister.NotFoundException {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }

    public void deleteById(Long id){
        commentRepository.deleteById(id);
    }

    public List<Comment> findByTaskId(Long taskId){
        return commentRepository.findByTaskId(taskId);
    }
}
