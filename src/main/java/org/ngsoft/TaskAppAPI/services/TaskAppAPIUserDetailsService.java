package org.ngsoft.TaskAppAPI.services;

import org.ngsoft.TaskAppAPI.entities.TaskAppAPIUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TaskAppAPIUserDetailsService  implements UserDetailsService {

    private final Map<String, TaskAppAPIUserDetails> users = new HashMap<>();

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.ngsoft.TaskAppAPI.entities.User user = null;
        try {
            user = userService.findByName(username);
            TaskAppAPIUserDetails userDetails = new TaskAppAPIUserDetails(user);
            return userDetails;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
    }

//    @PostConstruct
//    public void initUsers() {
//        Set<String> adminRoles = new HashSet<>();
//        adminRoles.add("ADMIN");
//        User admin = new User(1L, "admin", "admin@admin.com", "admin", true, true);
//        userService.save(admin);
//        TaskAppAPIUserDetails adminUserDetails = new TaskAppAPIUserDetails(admin);
//        users.put("admin", adminUserDetails);
//    }
}
