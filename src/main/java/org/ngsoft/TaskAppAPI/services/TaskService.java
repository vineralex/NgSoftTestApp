package org.ngsoft.TaskAppAPI.services;

import org.ngsoft.TaskAppAPI.entities.Comment;
import org.ngsoft.TaskAppAPI.entities.Status;
import org.ngsoft.TaskAppAPI.entities.Task;
import org.ngsoft.TaskAppAPI.projections.CommentProjection;
import org.ngsoft.TaskAppAPI.repositories.CommentRepository;
import org.ngsoft.TaskAppAPI.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) throws ChangeSetPersister.NotFoundException {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    public List<Task> findByAssignee(Long assignee) {
        return taskRepository.findByAssignee(assignee);
    }

    public List<Task> findByAssigneeAndStatusNotArchived(Long assignee) {

        List<Task> tasks = taskRepository.findByAssigneeAndStatusNot(assignee, Status.ARCHIVED);
        for (Task task : tasks) {
            List<CommentProjection> commentProjections = commentRepository.findCommentsByTaskId(task.getId());
            task.setComments(commentProjections);
        }

        return tasks;
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public Task saveComment(Task task, Comment comment) {
        commentRepository.save(comment);
        List<CommentProjection> commentProjections = commentRepository.findCommentsByTaskId(task.getId());
        task.setComments(commentProjections);
        return task;
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
