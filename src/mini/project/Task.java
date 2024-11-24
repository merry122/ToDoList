package mini.project;

public interface Task {

    // Method to get the task details
    String getDescription();
	
    // Method to set or update the task description
    void setDescription(String description);
    
    // Method to mark the task as completed
    void markAsCompleted();
    
    // Method to check if the task is completed
    boolean isCompleted();
    
    // Method to delete the task (optional for interfaces, but can return a status or log)
    void deleteTask();

}
