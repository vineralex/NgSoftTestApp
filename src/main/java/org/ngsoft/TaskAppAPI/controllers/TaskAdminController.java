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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/tasks")
public class TaskAdminController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<Task> findAll() {
        return taskService.findAll();
    }

    @PostMapping("/")
    public Task create(@RequestBody Task task) {
        return  taskService.save(task);
    }

    @PutMapping("/")
    public Task update(@RequestBody Task task) {
        return  taskService.save(task);
    }

    @PutMapping("/mark_completed/{id}")
    public Task markCompleted(@PathVariable Long id) {
        Task task = null;
        try {
            task = taskService.findById(id);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
        task.setStatus(Status.COMPLETED);
        return taskService.save(task);
    }

    @PutMapping("/mark_archived/{id}")
    public Task markArchived(@PathVariable Long id) {
        Task task = null;
        try {
            task = taskService.findById(id);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
        if (task.getStatus() == Status.COMPLETED) {
            task.setStatus(Status.ARCHIVED);
            return taskService.save(task);
        }
        return  task;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        taskService.deleteById(id);
    }

    @PostMapping("/comment/{id}")
    public Task commentTask(@PathVariable Long id, @RequestBody Comment comment, @AuthenticationPrincipal Jwt authenticatedUser) {
        Task task = null;
        try {
            task = taskService.findById(id);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }

        // bring admin id from auth
        Long currentUserId = 0L;
        try {
            currentUserId = Long.valueOf(authenticatedUser.getClaim("sub"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        User user = null;
        try {
            user = userService.findById(currentUserId);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }

        comment.setTask(task);
        comment.setUser(user);
        return taskService.saveComment(task,comment);
    }

    @PutMapping("/assign/{id}/{userId}")
    public Task assignToUser(@PathVariable Long id, @PathVariable Long userId) {
        Task task = null;
        try {
            task = taskService.findById(id);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
        task.setAssignee(userId);
        return  taskService.save(task);
    }
}
