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
    public abstract void addTask(TaskImpl task);

    /**
     *  Method to remove a task from the list
     * @param task attribute of type Task
     */
    public abstract void deleteTask(TaskImpl task);


  
    public abstract void editTask(TaskImpl task);

    /**
     *  Abstract method to display all tasks
     */
    public abstract void displayTasks();
}
