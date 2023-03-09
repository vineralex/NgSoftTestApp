package org.ngsoft.TaskAppAPI.projections;

import java.time.LocalDateTime;

public interface CommentProjection {
    Long getId();
    LocalDateTime getDateTime();
    String getComment();
}
