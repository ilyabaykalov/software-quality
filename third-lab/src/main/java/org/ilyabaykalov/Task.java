package org.ilyabaykalov;

import java.util.Date;
import java.util.Objects;

import static org.ilyabaykalov.Task.TaskPriority.MEDIUM;
import static org.ilyabaykalov.Task.TaskStatus.NOT_FINISHED;

public class Task {

    public enum TaskStatus {
        NOT_FINISHED,
        FINISHED,
        ARCHIVED
    }

    public enum TaskPriority {
        HIGH,
        MEDIUM,
        LOW
    }

    private int id;
    private String taskTitle;
    private String taskDescription;
    private String author;
    private Date dateOfCreation;
    private Date dateOfLastEditing;
    private TaskStatus status;
    private TaskPriority priority;

    public Task() {
        this.dateOfCreation = new Date();
        this.status = NOT_FINISHED;
        this.priority = MEDIUM;
    }

    public Task(Integer id, String taskTitle, String taskDescription, TaskStatus status) {
        this.id = id;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.status = status;
    }

    public Task(String taskTitle, String taskDescription) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.dateOfCreation = new Date();
        this.status = NOT_FINISHED;
    }

    public Task(String taskTitle, String taskDescription, String author) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.author = author;
        this.dateOfCreation = new Date();
        this.status = NOT_FINISHED;
        this.priority = MEDIUM;
    }

    public Task(String taskTitle, String taskDescription, String author, Date dateOfCreation, Date dateOfLastEditing, TaskStatus status, TaskPriority priority) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.author = author;
        this.dateOfCreation = dateOfCreation;
        this.dateOfLastEditing = dateOfLastEditing;
        this.status = status;
        this.priority = priority;
    }

    public Task(String taskTitle, String taskDescription, String author, TaskStatus status, TaskPriority priority) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.author = author;
        this.status = status;
        this.priority = priority;
    }

    public Task(int id, String taskTitle, String taskDescription, String author, Date dateOfCreation, Date dateOfLastEditing, TaskStatus status, TaskPriority priority) {
        this.id = id;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.author = author;
        this.dateOfCreation = dateOfCreation;
        this.dateOfLastEditing = dateOfLastEditing;
        this.status = status;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Date getDateOfLastEditing() {
        return dateOfLastEditing;
    }

    void setDateOfLastEditing(Date dateOfLastEditing) {
        this.dateOfLastEditing = dateOfLastEditing;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskTitle.equals(task.getTaskTitle()) &&
                taskDescription.equals(task.getTaskDescription()) &&
                author.equals(task.getAuthor()) &&
                status.equals(task.getStatus()) &&
                priority.equals(task.getPriority());
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskTitle, taskDescription, author, status, priority);
    }
}
