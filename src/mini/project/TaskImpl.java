package mini.project;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *  *@author meriem harrouz
 * Concrete implementation of the Task interface with enhanced features.
 * Includes priority, subtasks, time tracking, and task status.
 */
public class TaskImpl implements Task {
    // Core attributes of the task
    private String name; // The name/title of the task
    private String description; // Detailed description of the task
    private LocalDate dueDate; // Due date for task completion
    private boolean isCompleted; // Indicates if the task is completed

    // New features
    private String priority; // Task priority: High, Medium, or Low
    private List<String> subtasks=new ArrayList<>(); // List to store subtasks
    private long timeSpent; // Time spent on the task in minutes

    /**
     * Enum representing task status.
     * Provides clear status options: PENDING, IN_PROGRESS, COMPLETED.
     */
    public enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }

    private Status status=Status.PENDING; // Default status is PENDING

    /**
     * Constructor to initialize a task with given details.
     *
     * @param name        The name/title of the task
     * @param description The detailed description of the task
     * @param dueDate     The due date of the task in "YYYY-MM-DD" format
     */
    public TaskImpl(String name,String description,String dueDate) {
        createTask(name,description,dueDate); // Reuses the createTask method for validation
    }

    /**
     * Creates or updates task details.
     * Validates input to ensure data integrity.
     *
     * @param name        The name/title of the task
     * @param description The description of the task
     * @param dueDate     The due date in "YYYY-MM-DD" format
     */
    @Override
    public void createTask(String name, String description, String dueDate) {
        if (name==null||name.isEmpty())   throw new IllegalArgumentException("Task name cannot be empty.");
        if (description == null || description.isEmpty())    throw new IllegalArgumentException("Task description cannot be empty.");
        try {
            this.dueDate = LocalDate.parse(dueDate);
            if (this.dueDate.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Due date cannot be in the past.");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD.");
        }

        this.name=name;
        this.description=description;
        this.isCompleted=false;
        this.status=Status.PENDING; // Set the task status to pending
    }

    /**
     * Reuses the createTask method for validation and updating details.
     *
     * @param newName        The updated name/title of the task
     * @param newDescription The updated description of the task
     * @param newDueDate     The updated due date in "YYYY-MM-DD" format
     */
    @Override
    public void editTask(String newName, String newDescription, String newDueDate) {
        createTask(newName, newDescription, newDueDate); // Reuse validation logic
    }

    /**
     * Deletes a task by resetting its attributes.
     */
    @Override
    public void deleteTask() {
        this.name=null;
        this.description=null;
        this.dueDate=null;
        this.isCompleted=false;
        this.priority=null;
        this.subtasks.clear();
        this.timeSpent=0;
        this.status=Status.PENDING;
    }

    /**
     * Marks the task as completed.
     * Updates both the status and the isCompleted flag.
     */
    @Override
    public void markAsCompleted() {
        this.isCompleted=true;
        this.status=Status.COMPLETED;
    }

    /**
     * Sets the priority of the task.
     *
     * @param priority The priority level: High, Medium, or Low
     */
    public void setPriority(String priority) {
        if (!priority.equalsIgnoreCase("High") &&
                !priority.equalsIgnoreCase("Medium") &&
                !priority.equalsIgnoreCase("Low")) {
            throw new IllegalArgumentException("Priority must be High, Medium, or Low.");
        }
        this.priority=priority;
    }

    /**
     * Gets the priority of the task.
     *
     * @return The priority level as a String
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Adds a subtask to the task.
     *
     * @param subtask The subtask description
     */
    public void addSubtask(String subtask) {
        subtasks.add(subtask);
    }

    /**
     * Removes a specific subtask.
     *
     * @param subtask The subtask to remove
     */
    public void removeSubtask(String subtask) {
        subtasks.remove(subtask);
    }

    /**
     * Gets the list of subtasks.
     *
     * @return A list of subtasks
     */
    public List<String> getSubtasks() {
        return subtasks;
    }

    /**
     * Adds time spent on the task.
     *
     * @param minutes Time in minutes to add
     */
    public void addTimeSpent(long minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Time spent cannot be negative.");
        }
        this.timeSpent += minutes;
    }

    /**
     * Gets the total time spent on the task.
     *
     * @return Time spent in minutes
     */
    public long getTimeSpent() {
        return timeSpent;
    }

    /**
     * Returns a formatted string containing task details.
     *
     * @return A string representing the task's details
     */
    @Override
    public String getTaskDetails() {
        String subtasksInfo = subtasks.isEmpty() ? "" : "\nSubtasks: " + String.join(", ", subtasks);
        return "Name: " + name +
                "\nDescription: " + description +
                "\nDue Date: " + dueDate +
                "\nPriority: " + (priority != null ? priority : "Not Set") +
                "\nStatus: " + status +
                "\nTime Spent: " + timeSpent + " minutes" +
                "\nCompleted: " + (isCompleted ? "Yes" : "No") +
                subtasksInfo;
    }

    // Getter methods for core attributes
    public String getName() {return name;}
    public String getDescription() {return description;}
    public LocalDate getDueDate() {return dueDate;}
    public boolean isCompleted() {return isCompleted;}
    public Status getStatus() {return status;}
}
