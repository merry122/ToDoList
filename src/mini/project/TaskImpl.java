package mini.project;
import java.time.LocalDate;

/**
 * @author meriem harrouz
 * Concrete implementation of the Task interface.
 * Represents an individual task with attributes like name, description, due date, and completion status.
 */
public class TaskImpl implements Task {
    private String name;             
    private String description;      
    private LocalDate dueDate;       
    private boolean isCompleted;     

    /**
     * Constructor to initialise a task with given details.
     * @param name The name/title of the task
     * @param description The detailed description of the task
     * @param dueDate The due date of the task in "YYYY-MM-DD" format
     */
    public TaskImpl(String name,String description,String dueDate) {
        this.name=name;
        this.description=description;
        this.dueDate=LocalDate.parse(dueDate);  
        this.isCompleted=false;                 
    }

    /**
     * Creates or updates task details.
     * @param name The new name/title of the task
     * @param description The new description of the task
     * @param dueDate The new due date of the task in "YYYY-MM-DD" format
     */
    @Override
    public void createTask(String name,String description,String dueDate) {
        this.name=name;
        this.description=description;
        this.dueDate=LocalDate.parse(dueDate);
        this.isCompleted=false;   
    }

    /**
     * Edits existing task details.
     * @param newName The updated name/title of the task
     * @param newDescription The updated description of the task
     * @param newDueDate The updated due date in "YYYY-MM-DD" format
     */
    @Override
    public void editTask(String newName,String newDescription,String newDueDate) {
        this.name=newName;
        this.description=newDescription;
        this.dueDate=LocalDate.parse(newDueDate);
    }

    /**
     * Marks the task as completed.
     * Changes the task status to indicate completion.
     */
    @Override
    public void markAsCompleted() {
        this.isCompleted=true;
    }

    /**
     * Deletes a task.
     * The actual deletion is managed by TaskList. This method could be expanded if needed.
     */
    @Override
    public void deleteTask() {
        // This method might reset attributes or be used in conjunction with a list removal operation
        this.name=null;
        this.description=null;
        this.dueDate=null;
        this.isCompleted=false;
    }

    /**
     * Returns a formatted string containing task details.
     * Useful for displaying task information in the console or GUI.
     * @return A string representing the task's details
     */
    @Override
    public String getTaskDetails() {
        return "Name: "+name+ 
               "\nDescription: "+description + 
               "\nDue Date: "+dueDate + 
               "\nCompleted: "+(isCompleted?"Yes":"No");
    }

    // Getter methods to access task attributes if needed elsewhere
    public String getName() {return name;}
    public String getDescription() {return description;}
    public LocalDate getDueDate() {return dueDate;}
    public boolean isCompleted() {return isCompleted;}
    
}
