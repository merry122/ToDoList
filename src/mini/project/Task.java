package mini.project;
/**
 *@author meriem harrouz 
 */
public interface Task {
	
    /**
     *  Method to create task details
     * @param name
     * @param description
     * @param dueDate
     */
    void createTask(String name,String description,String dueDate);

    /**
     *  Method to edit task details
     * @param newName
     * @param newDescription
     * @param newDueDate
     */
    void editTask(String newName,String newDescription,String newDueDate);

    /**
     *  Method to delete the task
     */
    void deleteTask();

    /**
     *  Method to mark the task as completed
     */
    void markAsCompleted();

    /**
     *  Method to display task details (optional, for easier debugging)
     * @return
     */
    String getTaskDetails();
}

