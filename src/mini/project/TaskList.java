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
    
    protected List<Task> tasks=new ArrayList<>();

    /**
     *  Method to add a task to the list
     * @param task attribute of type Task
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     *  Method to remove a task from the list
     * @param task attribute of type Task
     */
    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    /**
     *  Method to get the task at a specific index
     * @param index  the index of the task we need to find
     * @return the task that was found according to the index
     */
    public Task getTask(int index) {
        if (index>=0&&index<tasks.size())    return tasks.get(index);
        else    throw new IndexOutOfBoundsException("Invalid task index");
    }

    /**
     *  Method to edit a task in the list
     * @param index
     * @param newName
     * @param newDescription
     * @param newDueDate
     */
    public void editTask(int index,String newName,String newDescription,String newDueDate) {
        Task task=getTask(index);
        task.editTask(newName,newDescription,newDueDate);
    }

    /**
     *  Abstract method to display all tasks
     */
    public abstract void displayTasks();
}
