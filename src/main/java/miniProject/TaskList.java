package miniProject;
/**
 * Abstract class for managing tasks in a to-do list application.
 * <p>
 * This class provides methods for adding, deleting, editing, retrieving, and filtering tasks.
 * Implementations of this class should define the specific behavior for managing tasks.
 * </p>
 * <p>
 * Author: Meriem Harrouz
 * </p>
 */
import java.util.List;

public abstract class TaskList {

    /**
     * Adds a task to the list.
     *
     * @param task the task to be added
     * @return true if the task was added successfully, false otherwise
     */
    public abstract boolean addTask(TaskImpl task);

    /**
     * Removes a task from the list.
     *
     * @param taskId the ID of the task to be removed
     * @return true if the task was removed successfully, false otherwise
     */
    public abstract void deleteTask(int taskId);

    /**
     * Edits an existing task.
     *
     * @param task the updated task details
     * @return true if the task was updated successfully, false otherwise
     */
    public abstract boolean editTask(TaskImpl task);

    /**
     * Retrieves all tasks.
     *
     * @return a list of all tasks
     */
    public abstract List<TaskImpl> getAll();

    /**
     * Sorts all tasks by due date.
     *
     * @return a list of tasks sorted by due date
     */
    public abstract List<TaskImpl> sortByDueDate();

    /**
     * Searches for tasks by their name.
     *
     * @param name the name or partial name of the tasks to be searched
     * @return a list of tasks matching the specified name
     */
    public abstract List<TaskImpl> searchByName(String name);
}