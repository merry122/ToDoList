package miniProject;
/**
 * Task interface with all the main operations for task management.
 * <p>
 * This interface outlines the basic methods for task creation, editing, deletion, completion,
 * and retrieving task details.
 * </p>
 *
 * @author Meriem Harrouz
 */
import java.time.LocalDate;


public interface Task {

    /**
     * Method to create a task with initial details.
     *
     * @param id          The unique identifier for the task.
     * @param name        The name of the task.
     * @param description A detailed description of the task.
     * @param dueDate     The due date for task completion.
     */
    void createTask(int id, String name, String description, LocalDate dueDate);

    /**
     * Method to edit the details of an existing task.
     *
     * @param id          The new unique identifier for the task.
     * @param name        The new name for the task.
     * @param description The new description for the task.
     * @param dueDate     The new due date for task completion.
     */
    void editTask(int id, String name, String description, LocalDate dueDate);

    /**
     * Method to delete the task, clearing all its details.
     */
    void deleteTask();

    /**
     * Method to mark the task as completed.
     */
    void markAsCompleted();

    /**
     * Method to retrieve task details in a human-readable format.
     *
     * @return A string containing the task details.
     */
    String getTaskDetails();

    /**
     * Method to update the task's status.
     *
     * @param status The new status of the task.
     */
    void updateStatus(Status status);



}
