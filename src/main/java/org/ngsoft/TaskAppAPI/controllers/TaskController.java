package org.ngsoft.TaskAppAPI.controllers;

import org.ngsoft.TaskAppAPI.entities.Comment;
import org.ngsoft.TaskAppAPI.entities.Status;
import org.ngsoft.TaskAppAPI.entities.Task;
import org.ngsoft.TaskAppAPI.entities.User;
import org.ngsoft.TaskAppAPI.services.CommentService;
import org.ngsoft.TaskAppAPI.services.TaskService;
import org.ngsoft.TaskAppAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public List<Task> myTasks(@PathVariable Long id) {
        // TODO: bring user id from auth
        // TODO: check if user is active
        return taskService.findByAssigneeAndStatusNotArchived(id);
    }

    @PutMapping("/mark_completed/{id}")
    public Task markCompleted(@PathVariable Long id) {
        Task task;
        try {
            task = taskService.findById(id);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
        // TODO: bring user id from auth
        // TODO: check if user is active
        // TODO: check if task assigned to the current user
        task.setStatus(Status.COMPLETED);
        return taskService.save(task);
    }

    @PostMapping("/comment/{id}")
    public Task commentTask(@PathVariable Long id, @RequestBody Comment comment) {
        Task task = null;
        try {
            task = taskService.findById(id);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }

        // TODO: bring user id from auth
        User user = null;
        try {
            user = userService.findById(1L);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
        // TODO: check if user is active
        // TODO: check if task assigned to the current user

        comment.setTask(task);
        comment.setUser(user);
        return taskService.saveComment(task,comment);
    }
}
