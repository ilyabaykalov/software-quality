package org.ilyabaykalov;

import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();

    List<Task> getArchivedTasks();

    List<Task> getNonArchivedTasks();

    Task getTask(int id);

    Task addTask(Task task);

    boolean editTask(Task task);

    boolean removeTask(int id);

    boolean setStatus(int id, String status);

    boolean setPriority(int id, Task.TaskPriority priority);
}
