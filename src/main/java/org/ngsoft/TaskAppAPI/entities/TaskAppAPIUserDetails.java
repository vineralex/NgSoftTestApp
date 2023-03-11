package org.ngsoft.TaskAppAPI.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;

@JsonAutoDetect
@Getter
@Setter
public class TaskAppAPIUserDetails extends User {

    private Long id;
    private String name;
    private String email;
    private Boolean isAdmin;
    private Boolean isActive;

    public TaskAppAPIUserDetails(org.ngsoft.TaskAppAPI.entities.User user) {
        super(user.getName(), user.getPassword(), user.getGrantedAuthoritiesList());

        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.isAdmin = user.getIsAdmin();
        this.isActive = user.getIsActive();
    }
}
