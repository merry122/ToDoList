package miniProject;
/**
 * Implementation of the Task interface, representing a task with various attributes and operations.
 * <p>
 * This class provides the concrete implementation for managing tasks, including creation, editing, deletion,
 * completion, and retrieval of task details.
 * </p>
 *
 * Author: Meriem Harrouz
 */
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskImpl implements Task {
    private int id; // ID of the task
    private String name; // The name/title of the task
    private String description; // Detailed description of the task
    private LocalDate dueDate; // Due date for task completion
    private boolean isCompleted; // Indicates if the task is completed
    private LocalDate completionDate; // Completion date of the task

    private Priority priority; // Task priority: High, Medium, or Low
    private Category category;  // Task category: Work, Personal, etc.
    private List<String> subtasks = new ArrayList<>(); // List to store subtasks
    private long timeSpent; // Time spent on the task in minutes
    private Status status; // Default status is PENDING

    public void setId(int id) {
        this.id=id;
    }

    // Getter for the completion date
    public LocalDate getCompletionDate() {
        return completionDate; // Return the completion date
    }

    /**
     * Enum representing task priority levels.
     */
    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    /**
     * Enum representing task categories.
     */
    public enum Category {
        WORK, PERSONAL, SHOPPING, GENERAL, OTHERS
    }

    /**
     * Enum representing the status of a task.
     */
    public enum Status {
        PENDING, IN_PROGRESS, COMPLETED
    }

    /**
     * Constructs a TaskImpl instance with the specified details.
     *
     * @param id          The unique identifier for the task.
     * @param name        The name/title of the task.
     * @param description A detailed description of the task.
     * @param dueDate     The due date for task completion.
     * @param status      The current status of the task.
     * @param priority    The priority level of the task.
     * @param category    The category of the task.
     */
    public TaskImpl(int id, String name, String description, LocalDate dueDate, Status status, Priority priority, Category category) {
        createTask(id, name, description, dueDate);
        this.status = status;
        this.priority = priority;
        this.category = category;
    }

    /**
     * Creates a new task with the provided details.
     *
     * @param id          The unique identifier for the task.
     * @param name        The name/title of the task.
     * @param description A detailed description of the task.
     * @param dueDate     The due date for task completion.
     * @throws IllegalArgumentException If any of the parameters are invalid.
     */
    @Override
    public void createTask(int id, String name, String description, LocalDate dueDate) {
        if (id <= 0) throw new IllegalArgumentException("Task ID must be a positive integer.");
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Task name cannot be empty.");
        if (description == null || description.isEmpty()) throw new IllegalArgumentException("Task description cannot be empty.");
        if (dueDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date cannot be in the past.");
        }

        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = false;
        this.status = Status.PENDING; // Default status
    }

    @Override
    public void editTask(int id, String name, String description, LocalDate dueDate) {

    }

    @Override
    public void deleteTask() {
        // This method should be used to remove the task from a list or database
        // If you want to reset the task, consider renaming this method
        resetTask();
    }

    private void resetTask() {
        this.id = 0;
        this.name = null;
        this.description = null;
        this.dueDate = null;
        this.isCompleted = false;
        this.priority = null;
        this.category = null;
        this.subtasks.clear();
        this.timeSpent = 0;
        this.status = Status.PENDING;
    }

    @Override
    public void markAsCompleted() {
        this.isCompleted = true;
        this.status = Status.COMPLETED;
    }

    public void addSubtask(String subtask) {
        subtasks.add(subtask);
    }

    public void removeSubtask(String subtask) {
        subtasks.remove(subtask);
    }

    public List<String> getSubtasks() {
        return subtasks;
    }

    public void addTimeSpent(long minutes) {
        if (minutes < 0) throw new IllegalArgumentException("Time spent cannot be negative.");
        this.timeSpent += minutes;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    @Override
    public String getTaskDetails() {
        String subtasksInfo = subtasks.isEmpty() ? "" : "\nSubtasks: " + String.join(", ", subtasks);
        return "Id: " + id +
                "\nName: " + name +
                "\nDescription: " + description +
                "\nDue Date: " + dueDate +
                "\nPriority: " + (priority != null ? priority : "Not Set") +
                "\nCategory: " + (category != null ? category : "Not Set") +
                "\nStatus: " + status +
                "\nTime Spent: " + timeSpent + " minutes" +
                "\nCompleted: " + (isCompleted ? "Yes" : "No") +
                subtasksInfo;
    }

    @Override
    public void updateStatus(miniProject.Status status) {

    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public Status getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }

    public Category getCategory() {
        return category;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        if (dueDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date cannot be in the past.");
        }
        this.dueDate = dueDate;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}