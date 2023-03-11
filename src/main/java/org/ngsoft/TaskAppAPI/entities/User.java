package org.ngsoft.TaskAppAPI.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Boolean isAdmin;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private String password;

    public User(Long id, String name, String email, String password, Boolean isActive, Boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.isAdmin = isAdmin;
    }

    public User() { }

    public Collection<GrantedAuthority> getGrantedAuthoritiesList(){
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("NA");

        if (this.isActive) {
            if (this.isAdmin) {
                grantedAuthority = new SimpleGrantedAuthority("ADMIN");
            } else {
                grantedAuthority = new SimpleGrantedAuthority("USER");
            }
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(grantedAuthority);
        return authorities;
    }
}