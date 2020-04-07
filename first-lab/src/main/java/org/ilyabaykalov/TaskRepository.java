package org.ilyabaykalov;

import java.util.List;

public interface TaskRepository {
    List<Task> findAllTask();

    List<Task> findArchivedTask();

    List<Task> findNonArchivedTask();

    Task get(int id);

    Task add(Task task);

    void update(Task task);

    boolean remove(int id);
}
