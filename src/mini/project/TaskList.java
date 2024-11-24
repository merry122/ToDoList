package mini.project;
/**
 *@author meriem harrouz 
 */


public abstract class TaskList {
	/**
	 *  it's a class that manages tasks
	 */
    /**
     *  Method to add a task to the list
     * @param task attribute of type Task
     */
    public abstract void addTask(Task task);

    /**
     *  Method to remove a task from the list
     * @param task attribute of type Task
     */
    public abstract void deleteTask(Task task);


    /**
     *  Method to edit a task in the list
     * @param index
     * @param newName
     * @param newDescription
     * @param newDueDate
     */
    public abstract void editTask(int index,String newName,String newDescription,String newDueDate);

    /**
     *  Abstract method to display all tasks
     */
    public abstract void displayTasks();
}
