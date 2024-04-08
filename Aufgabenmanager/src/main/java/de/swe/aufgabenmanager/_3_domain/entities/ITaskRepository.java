package de.swe.aufgabenmanager._3_domain.entities;

import java.util.List;

public interface ITaskRepository extends ISave<Task>, IDelete<Task>, IFindAll {

    List<Task> findByUserId(Long userId);
    List<Task> findByGroupId(Long groupId);
}
