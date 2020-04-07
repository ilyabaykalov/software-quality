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

    /**
     * Получение всех задач из репозитория
     *
     * @return Коллекция всех задач
     * @author Ilya Baykalov
     */
    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAllTask();
    }

    /**
     * Получение всех архивных задач из репозитория
     *
     * @return Коллекция всех архивных задач
     * @author Ilya Baykalov
     */
    @Override
    public List<Task> getArchivedTasks() {
        return taskRepository.findArchivedTask();
    }

    /**
     * Получение всех задач кроме архивных из репозитория
     *
     * @return Коллекция всех задач кроме архивных
     * @author Ilya Baykalov
     */
    @Override
    public List<Task> getNonArchivedTasks() {
        return taskRepository.findNonArchivedTask();
    }

    /**
     * Получение конкретной задачи из репозитория
     *
     * @param id Идектификационный номер задачи
     * @return Конкретная задача
     * @author Ilya Baykalov
     */
    @Override
    public Task getTask(int id) {
        return taskRepository.get(id);
    }

    /**
     * Добавление новой задачи в репозиторий
     *
     * @param task Новая задача
     * @return Созданная задача
     * @author Ilya Baykalov
     */
    @Override
    public Task addTask(Task task) {
        if (task.getStatus().equals(NOT_FINISHED)) {
            task.setAuthor(userService.getCurrentUser());
            return taskRepository.add(task);
        } else {
            throw new RuntimeException("Нельзя создать задачу");
        }
    }

    /**
     * Получение задачи из строки
     *
     * @param line Строка, содержащая задачу
     * @return Полученная задача
     * @author Ilya Baykalov
     */
    @Override
    public Task parseTask(String line) {
        if (line != null) {
            if (!line.equals("")) {
                String[] parseData = line.split("_");
                return new Task(parseData[0], parseData[1], parseData[2]);
            }
        }
        return null;
    }

    /**
     * Изменение задачи
     *
     * @param task Выбранная задача
     * @return Статус выполнения (true - выполнено, false - не выполнено)
     * @author Ilya Baykalov
     */
    @Override
    public boolean editTask(Task task) {
        String author = taskRepository.get(task.getId()).getAuthor();
        String currentUser = userService.getCurrentUser();
        if (author.equals(currentUser)) {
            task.setDateOfLastEditing(new Date());
            taskRepository.update(task);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Удаление задачи из репозитория
     *
     * @param id Идектификационный номер задачи
     * @return Статус выполнения (true - выполнено, false - не выполнено)
     * @author Ilya Baykalov
     */
    @Override
    public boolean removeTask(int id) {
        try {
            return taskRepository.remove(id);
        } catch (RuntimeException rex) {
            throw new RuntimeException(MessageFormat.format("Задачи с id: {0} не существует", id));
        }
    }

    /**
     * Возвращает все задачи из репозитория
     *
     * @param id     Идектификационный номер задачи
     * @param status Новый статус задачи
     * @return Статус выполнения (true - выполнено, false - не выполнено)
     * @author Ilya Baykalov
     */
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

    /**
     * Возвращает все задачи из репозитория
     *
     * @param id       Идектификационный номер задачи
     * @param priority Новый приоритет задачи
     * @return Статус выполнения (true - выполнено, false - не выполнено)
     * @author Ilya Baykalov
     */
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
