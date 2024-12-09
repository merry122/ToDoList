package miniProject;
/**
 *@author meriem harrouz
 *and interface with all the main operations
 */
public interface Task {

    /**
     *  Method to create task details
     * @param id
     * @param name
     * @param description
     * @param dueDate
     */
    void createTask(int id,String name,String description,String dueDate);

    /**
     *  Method to edit task details
     * @param newId
     * @param newName
     * @param newDescription
     * @param newDueDate
     */
    void editTask(int newId,String newName,String newDescription,String newDueDate);

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
