package mini.project;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

/**
 * @author meriem harrouz
 * Concrete class extending the abstract TaskList.
 * Manages a collection of tasks and provides functionality to display them.
 */
public class TaskListImpl extends TaskList {
    private ArrayList<TaskImpl> tasks;
    public TaskListImpl(){
        tasks=new ArrayList<>();
    }
    /**
     @author raouane krama
     **/
    /**method to add a task to the database**/
    public void addTask(TaskImpl task){
        Connection con=DBconnection.getConnection();
        if(con==null){
            return;
        }
        String query="INSERT INTO Tasks (name,description,dueDate,is_completed) VALUES (?,?,?,?);";
        try(PreparedStatement preparedStatement=con.prepareStatement(query)){
            preparedStatement.setString(1,task.getName());
            preparedStatement.setString(2,task.getDescription());
            preparedStatement.setDate(3, Date.valueOf(task.getDueDate()));
            preparedStatement.setBoolean(4,task.isCompleted());

            preparedStatement.executeUpdate();
            System.out.println("Task is add to DataBase!");
        }catch(SQLException mess){
            mess.printStackTrace();
        } finally {
            try{
                con.close();
            }catch(SQLException mess){
                mess.printStackTrace();
            }
        }
    }
    /**method to UPDATE a task in the database**/
    public void editTask(TaskImpl task){
        Connection con=DBconnection.getConnection();
        if(con==null){
            return;
        }
        String query="UPDATE Tasks SET name=?, description=?, dueDate=?, is_completed=? WHERE name=?;";
        try(PreparedStatement preparedStatement=con.prepareStatement(query)){
            preparedStatement.setString(1,task.getName());
            preparedStatement.setString(2,task.getDescription());
            preparedStatement.setDate(3, Date.valueOf(task.getDueDate()));
            preparedStatement.setBoolean(4,task.isCompleted());

            preparedStatement.executeUpdate();
            System.out.println("Task is UPDATE successfully!");
        }catch(SQLException mess){
            mess.printStackTrace();
        } finally {
            try{
                con.close();
            }catch(SQLException mess){
                mess.printStackTrace();
            }
        }

    }
    /**method to delete a task from the database**/
    public void deleteTask(TaskImpl task){
        Connection con=DBconnection.getConnection();
        if(con==null){
            return;
        }
        String query="DELETE FROM Tasks WHERE name=?;";
        try(PreparedStatement preparedStatement=con.prepareStatement(query)){
            preparedStatement.setString(1,task.getName());

            preparedStatement.executeUpdate();
        }catch(SQLException mess){
            mess.printStackTrace();
        }finally{
            try{
                con.close();
            }catch(SQLException mess){
                mess.printStackTrace();
            }
        }
    }
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
