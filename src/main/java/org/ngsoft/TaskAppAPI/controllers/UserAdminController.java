package org.ngsoft.TaskAppAPI.controllers;

import org.ngsoft.TaskAppAPI.entities.User;
import org.ngsoft.TaskAppAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/user")
    public String getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        // Do something with the username
        return "Current User: " + username;
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        try {
            return userService.findById(id);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public User create(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.save(user);
    }

    @PutMapping
    public User save(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/activate/{id}/{isActivate}")
    public User activate(@PathVariable Long id, @PathVariable Boolean isActivate) {
        User user = null;
        try {
            user = userService.findById(id);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
        user.setIsActive(isActivate);
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
