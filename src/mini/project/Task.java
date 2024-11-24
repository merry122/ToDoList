package mini.project;

public interface Task {
	
    // Method to create task details
    void createTask(String name,String description,String dueDate);

    // Method to edit task details
    void editTask(String newName,String newDescription,String newDueDate);

    // Method to delete the task
    void deleteTask();

    // Method to mark the task as completed
    void markAsCompleted();

    // Method to display task details (optional, for easier debugging)
    String getTaskDetails();
}

