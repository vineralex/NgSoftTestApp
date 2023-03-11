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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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

    @GetMapping("/")
    public List<Task> myTasks(@AuthenticationPrincipal Jwt authenticatedUser) {
        Long currentUserId = Long.valueOf(authenticatedUser.getClaim("sub"));
        return taskService.findByAssigneeAndStatusNotArchived(currentUserId);
    }

    @PutMapping("/mark_completed/{id}")
    public ResponseEntity<Task> markCompleted(@PathVariable Long id, @AuthenticationPrincipal Jwt authenticatedUser) {
        Task task;
        try {
            task = taskService.findById(id);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }

        // bring user id from auth
        Long currentUserId = 0L;
        try {
            currentUserId = Long.valueOf(authenticatedUser.getClaim("sub"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // check if task assigned to the current user
        if (task.getAssignee() != currentUserId) {
            return ResponseEntity.badRequest().body(task);
        }

        task.setStatus(Status.COMPLETED);
        taskService.save(task);
        return ResponseEntity.ok(task);
    }

    @PostMapping("/comment/{id}")
    public ResponseEntity<Task> commentTask(@PathVariable Long id, @RequestBody Comment comment, @AuthenticationPrincipal Jwt authenticatedUser) {
        Task task = null;
        try {
            task = taskService.findById(id);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }

        // bring user id from auth
        Long currentUserId = 0L;
        try {
            currentUserId = Long.valueOf(authenticatedUser.getClaim("sub"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // check if task assigned to the current user
        if (task.getAssignee() != currentUserId) {
            return ResponseEntity.badRequest().body(task);
        }

        User user = null;
        try {
            user = userService.findById(currentUserId);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
        comment.setTask(task);
        comment.setUser(user);
        taskService.saveComment(task,comment);
        return ResponseEntity.ok(task);
    }
}
