package org.ngsoft.TaskAppAPI.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.ngsoft.TaskAppAPI.projections.CommentProjection;

import java.util.List;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;


    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    @Column(nullable = false)
    private Long assignee;

    @OneToMany(mappedBy = "task")
    @Transient
    private List<CommentProjection> comments;
}
