package org.ngsoft.TaskAppAPI.repositories;

import org.ngsoft.TaskAppAPI.entities.Status;
import org.ngsoft.TaskAppAPI.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignee(Long assignee);
    List<Task> findByAssigneeAndStatusNot(Long assignee, Status status);
}
