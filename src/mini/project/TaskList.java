package mini.project;

import java.util.ArrayList;
import java.util.List;

public abstract class TaskList {
	
    // List to store tasks
    protected List<Task> tasks=new ArrayList<>();

    // Method to add a task to the list
    public void addTask(Task task) {
        tasks.add(task);
    }

    // Method to remove a task from the list
    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    // Method to get the task at a specific index
    public Task getTask(int index) {
        if (index>=0&&index<tasks.size())    return tasks.get(index);
        else    throw new IndexOutOfBoundsException("Invalid task index");
    }

    // Method to edit a task in the list
    public void editTask(int index,String newName,String newDescription,String newDueDate) {
        Task task=getTask(index);
        task.editTask(newName,newDescription,newDueDate);
    }

    // Abstract method to display all tasks (implementation depends on the subclass)
    public abstract void displayTasks();
}
