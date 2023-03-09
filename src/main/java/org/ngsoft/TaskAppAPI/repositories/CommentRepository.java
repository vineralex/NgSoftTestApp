package org.ngsoft.TaskAppAPI.repositories;

import org.ngsoft.TaskAppAPI.entities.Comment;
import org.ngsoft.TaskAppAPI.projections.CommentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTaskId(Long taskId);
    List<CommentProjection> findCommentsByTaskId(Long taskId);

}
