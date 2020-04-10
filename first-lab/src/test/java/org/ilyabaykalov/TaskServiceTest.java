package org.ilyabaykalov;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.MessageFormat;
import java.util.*;

import static org.ilyabaykalov.Task.TaskPriority.HIGH;
import static org.ilyabaykalov.Task.TaskPriority.LOW;
import static org.ilyabaykalov.Task.TaskStatus.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest extends Assert {

    @Mock
    TaskRepository taskRepository;

    @Mock
    UserService userService;

    private TaskService taskService;

    private int lastTaskId = -1;
    private Map<Integer, Task> taskStore = new HashMap<>();

    @Before
    public void prepare() {
        when(userService.getCurrentUser()).thenReturn("ilyabaykalov");

        when(taskRepository.add(any(Task.class))).then(invocationOnMock -> {
            Task newTask = invocationOnMock.getArgument(0);
            lastTaskId += 1;
            newTask.setId(lastTaskId);
            taskStore.put(lastTaskId, newTask);
            return newTask;
        });

        doAnswer(invocationOnMock -> {
            int id = invocationOnMock.getArgument(0);
            if (taskStore.containsKey(id)) {
                taskStore.remove(id);
                return true;
            } else {
                throw new RuntimeException(MessageFormat.format("Не могу удалить задачу. Задачи с id: {0} не существует", id));
            }
        }).when(taskRepository).remove(anyInt());

        when(taskRepository.findAllTask()).then(invocationOnMock -> new ArrayList<>(taskStore.values()));

        when(taskRepository.get(anyInt())).then(invocationOnMock -> {
            int id = invocationOnMock.getArgument(0);

            return taskStore.getOrDefault(id, null);
        });

        doAnswer(invocationOnMock -> {
            Task task = invocationOnMock.getArgument(0);
            if (taskStore.containsValue(task)) {
                return task.getStatus();
            } else return null;
        }).when(taskRepository).update(any(Task.class));

        taskService = new TaskServiceImpl(taskRepository, userService);
    }

    @Test
    public void getTaskTest() {
        Task task = new Task("Первая задача", "Описание задачи");
        taskService.addTask(task);

        Task fromService = taskService.getTask(anyInt());

        assertEquals(task.getTaskTitle(), fromService.getTaskTitle());
        assertEquals(task.getTaskDescription(), fromService.getTaskDescription());
        assertEquals(fromService.getId(), fromService.getId());

        assertEquals(fromService.getStatus(), NOT_FINISHED);
    }

    @Test
    public void createRemoveTaskTest() {
        Task createdTask = new Task("Первая задача", "Описание задачи");
        Task fromService = taskService.addTask(createdTask);

        assertEquals(createdTask.getTaskTitle(), fromService.getTaskTitle());
        assertEquals(createdTask.getTaskDescription(), fromService.getTaskDescription());
        assertEquals(fromService.getId(), fromService.getId());

        assertEquals(fromService.getStatus(), NOT_FINISHED);

        assertTrue(taskService.removeTask(fromService.getId()));
    }

    @Test(expected = RuntimeException.class)
    public void cantCreateFinishedTaskTest() {
        Task task = new Task("Эта задача не должна сохраниться", "Описание задачи");
        task.setStatus(FINISHED);

        taskService.addTask(task);
    }

    @Test(expected = RuntimeException.class)
    public void cantCreateArchivedTaskTest() {
        Task task = new Task("Эта задача не должна сохраниться", "Описание задачи");
        task.setStatus(ARCHIVED);

        taskService.addTask(task);
    }

    @Test
    public void addManyTaskAndThenGetTest() {
        for (int i = 1; i <= 10; i++) {
            taskService.addTask(new Task("Задача: " + i, "Описание: " + i));
        }

        List<Task> taskList = taskService.getAllTasks();
        assertEquals(10, taskList.size());

        for (Task task : taskList) {
            assertTrue(taskService.removeTask(task.getId()));
        }

        taskList = taskService.getAllTasks();

        assertEquals(0, taskList.size());
    }

    @Test
    public void canAuthorEditTaskTest() {
        Task task = new Task("Первая задача", "Описание задачи");
        taskService.addTask(task);

        task.setTaskTitle("Изменил название");
        assertEquals(task.getTaskTitle(), taskService.getTask(task.getId()).getTaskTitle());
    }

    @Test(expected = RuntimeException.class)
    public void removeNonexistentTaskTest() {
        Task task = new Task("Первая задача", "Описание задачи");
        taskService.addTask(task);

        assertTrue(taskService.removeTask(task.getId()));
        assertFalse(taskService.removeTask(task.getId()));
    }

    @Test
    public void changeTaskStatusTest() {
        Task task = new Task("Первая задача", "Описание задачи");
        taskService.addTask(task);


        taskService.setStatus(task.getId(), "FINISHED");
        assertEquals(task.getStatus(), FINISHED);

        taskService.setStatus(task.getId(), "NOT_FINISHED");
        assertEquals(task.getStatus(), NOT_FINISHED);
    }

    @Test
    public void cantOtherUserChangeStatusTaskTest() {
        Task task = new Task("Первая задача", "Описание задачи");
        taskService.addTask(task);

        when(userService.getCurrentUser()).thenReturn("andrewtelkov");

        assertFalse(taskService.setStatus(task.getId(), "FINISHED"));
    }

    @Test
    public void canAuthorChangeStatusTaskTest() {
        Task task = new Task("Первая задача", "Описание задачи");
        taskService.addTask(task);

        assertTrue(taskService.setStatus(task.getId(), "FINISHED"));
    }

    @Test
    public void cantOtherUserChangePriorityTaskTest() {
        Task task = new Task("Первая задача", "Описание задачи");
        taskService.addTask(task);
        taskService.setPriority(task.getId(), HIGH);
        when(userService.getCurrentUser()).thenReturn("andrewtelkov");

        assertFalse(taskService.setPriority(task.getId(), LOW));
    }

    @Test
    public void canAuthorChangePriorityTaskTest() {
        Task task = new Task("Первая задача", "Описание задачи");
        taskService.addTask(task);

        assertTrue(taskService.setPriority(task.getId(), LOW));
    }

    @Test
    public void testAssertEquals() {
        Task task1 = new Task("Первая задача", "Описание задачи", "ilyabaykalov", FINISHED, LOW);
        Task task2 = new Task("Первая задача", "Описание задачи", "ilyabaykalov", FINISHED, LOW);

        Task task3 = new Task("Первая задача", "Описание задачи", "andrewtelkov", FINISHED, LOW);
        Task task4 = new Task("Вторая задача", "Описание задачи", "ilyabaykalov", FINISHED, LOW);
        Task task5 = new Task("Первая задача", "Описание третьей задачи", "ilyabaykalov", FINISHED, LOW);
        Task task6 = new Task("Первая задача", "Описание задачи", "andrewtelkov", NOT_FINISHED, LOW);
        Task task7 = new Task("Первая задача", "Описание задачи", "andrewtelkov", NOT_FINISHED, HIGH);

        assertEquals(task1, task2);
        assertNotEquals(task1, task3);
        assertNotEquals(task1, task4);
        assertNotEquals(task1, task5);
        assertNotEquals(task1, task6);
        assertNotEquals(task1, task7);
    }

    @Test
    public void testParserTask() {
        String line1 = "Задача 1_Описание 1_ilyabaykalov";
        String line2 = "Задача 1%Описание 1%ilyabaykalov";

        Task task1 = new Task("Задача 1", "Описание 1", "ilyabaykalov");
        Task task2 = new Task("Задача 2", "Описание 2", "ilyabaykalov");

        Task parsedTask1 = taskService.parseTask(line1);
        Task parsedTask2 = taskService.parseTask(line2);
        Task parsedTask3 = taskService.parseTask("");
        Task parsedTask4 = taskService.parseTask(null);

        assertNotEquals(task2, parsedTask1);
        assertEquals(task1, parsedTask1);

        assertNull(parsedTask2);
        assertNull(parsedTask3);
        assertNull(parsedTask4);
    }
}
