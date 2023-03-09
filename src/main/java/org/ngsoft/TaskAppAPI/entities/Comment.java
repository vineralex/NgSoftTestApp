package org.ngsoft.TaskAppAPI.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "taskId", nullable = false)
    private Task task;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String comment;

    @PrePersist
    public void setDefaultDateTime() {
        if (dateTime == null) {
            dateTime = LocalDateTime.now();
        }
    }
}
