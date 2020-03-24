package org.ilyabaykalov;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import static org.ilyabaykalov.Task.TaskStatus.*;

public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private UserService userService;

    public TaskServiceImpl(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAllTask();
    }

    @Override
    public List<Task> getArchivedTasks() {
        return taskRepository.findArchivedTask();
    }

    @Override
    public List<Task> getNonArchivedTasks() {
        return taskRepository.findNonArchivedTask();
    }

    @Override
    public Task getTask(int id) {
        return taskRepository.get(id);
    }

    @Override
    public Task addTask(Task task) {
        if (task.getStatus().equals(NOT_FINISHED)) {
            task.setAuthor(userService.getCurrentUser());
            return taskRepository.add(task);
        } else {
            throw new RuntimeException("Нельзя создать задачу");
        }
    }

    @Override
    public boolean editTask(Task task) {
        String author = taskRepository.get(task.getId()).getAuthor();
        String currentUser = userService.getCurrentUser();
        if (author.equals(currentUser)) {
//            if (task.getStatus().equals(NOT_FINISHED)) {
                task.setDateOfLastEditing(new Date());
                taskRepository.update(task);
                return true;
//            }
        } else {
            return false;
        }
    }

    @Override
    public boolean removeTask(int id) {
        try {
            return taskRepository.remove(id);
        } catch (RuntimeException rex) {
            throw new RuntimeException(MessageFormat.format("Задачи с id: {0} не существует", id));
        }
    }

    @Override
    public boolean setStatus(int id, String status) {
        String author = taskRepository.get(id).getAuthor();
        String currentUser = userService.getCurrentUser();
        if (author.equals(currentUser)) {
            Task task = taskRepository.get(id);

            switch (status) {
                case "NOT_FINISHED":
                    task.setStatus(NOT_FINISHED);
                    break;
                case "FINISHED":
                    task.setStatus(FINISHED);
                    break;
                case "ARCHIVED":
                    if (task.getStatus().equals(FINISHED))
                        task.setStatus(ARCHIVED);
                    else return false;
            }
            taskRepository.update(task);
            return true;
        }
        return false;
    }

    @Override
    public boolean setPriority(int id, Task.TaskPriority priority) {
        String author = taskRepository.get(id).getAuthor();
        String currentUser = userService.getCurrentUser();
        if (author.equals(currentUser)) {
            Task task = taskRepository.get(id);
            task.setPriority(priority);
            taskRepository.update(task);
            return true;
        }
        return false;
    }
}
