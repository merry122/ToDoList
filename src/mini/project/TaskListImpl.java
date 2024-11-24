package mini.project;

/**
 * @author meriem harrouz
 * Concrete class extending the abstract TaskList.
 * Manages a collection of tasks and provides functionality to display them.
 */
public class TaskListImpl extends TaskList {

    /**
     * Displays all tasks in the task list.
     * If the list is empty, a message is shown to inform the user.
     */
    @Override
    public void displayTasks() {
        // Check if the task list is empty
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");  // display that the list is empty
            return;  // Exit the method 
        }

        // Iterate through the list of tasks and display each one
        System.out.println("Task List:");
        System.out.println("-----------");
        for (int i=0;i<tasks.size();i++) {
            // Print task index (starting from 1 for user-friendly display)
            System.out.println("Task "+(i+1)+":");
            
            // Retrieve task details using the getTaskDetails() method from TaskImpl
            System.out.println(tasks.get(i).getTaskDetails());
            
            // Add a separator between tasks for better readability
            System.out.println("-----------------------------");
        }
    }
}
