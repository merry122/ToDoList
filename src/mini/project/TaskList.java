package mini.project;
/**
 *@author meriem harrouz 
 */
import java.util.ArrayList;
import java.util.List;

public abstract class TaskList {
	/**
	 *  it's a class that manages tasks
	 */
    /**
     *  Method to add a task to the list
     * @param task attribute of type Task
     */
    public abstract addTask(Task task);

    /**
     *  Method to remove a task from the list
     * @param task attribute of type Task
     */
    public abstract deleteTask(Task task);


    /**
     *  Method to edit a task in the list
     * @param index
     * @param newName
     * @param newDescription
     * @param newDueDate
     */
    public abstract editTask(int index,String newName,String newDescription,String newDueDate);

    /**
     *  Abstract method to display all tasks
     */
    public abstract void displayTasks();
}
