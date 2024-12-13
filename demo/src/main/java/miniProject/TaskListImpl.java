package miniProject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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
     * @author raouane krama
     */
    public List<TaskImpl> getAll() {
        Connection con = DBconnection.getConnection();
        if (con == null) {
            System.err.println("Failed to establish database connection.");
            return Collections.emptyList();
        }

        String query = "SELECT id, name, description, dueDate, is_completed FROM tasks;";
        List<TaskImpl> tasks = new ArrayList<>();

        try (PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Date dueDate = resultSet.getDate("dueDate");
                boolean isCompleted = resultSet.getBoolean("is_completed");

                TaskImpl task = new TaskImpl(id, name, description, dueDate.toString());
                task.setCompleted(isCompleted);
                tasks.add(task);
            }

            System.out.println("Fetched " + tasks.size() + " tasks from the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return tasks;
    }


    /**
     * Method to add a task to the database.
     * The task details are inserted into the database using an INSERT statement.
     * After successful insertion, a message is printed to confirm the task has been added.
     */
    public void addTask(TaskImpl task){
        Connection con=DBconnection.getConnection();
        if(con==null){
            return;
        }
        String query="INSERT INTO Tasks (id,name,description,dueDate,is_completed) VALUES (?,?,?,?,?);";
        try(PreparedStatement preparedStatement=con.prepareStatement(query)){
            preparedStatement.setInt(1,task.getId());
            preparedStatement.setString(2,task.getName());
            preparedStatement.setString(3,task.getDescription());
            preparedStatement.setDate(4, Date.valueOf(task.getDueDate()));
            preparedStatement.setBoolean(5,task.isCompleted());

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

    /**
     * Method to update an existing task in the database.
     * The task details are updated based on the task's ID.
     * After the update, a message is printed to confirm the task has been updated.
     */
    public void editTask(TaskImpl task) {
        Connection con = DBconnection.getConnection();
        if (con == null) {
            System.err.println("Database connection failed!");
            return;
        }

        String query = "UPDATE Tasks SET name = ?, description = ?, dueDate = ?, is_completed = ? WHERE id = ?;";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setDate(3, Date.valueOf(task.getDueDate())); // Assumes getDueDate() returns a valid date as String
            preparedStatement.setBoolean(4, task.isCompleted());
            preparedStatement.setInt(5, task.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Task updated successfully!");
            } else {
                System.err.println("No task was updated. Please check the id.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException mess) {
                mess.printStackTrace();
            }
        }
    }

    /**
     * Method to delete a task from the database.
     * The task is deleted based on its ID using a DELETE SQL statement.
     * After the task is deleted, no confirmation message is printed, but the task is removed.
     */
    public void deleteTask(TaskImpl task){
        Connection con=DBconnection.getConnection();
        if(con==null){
            return;
        }
        String query="DELETE FROM Tasks WHERE id=?;";
        try(PreparedStatement preparedStatement=con.prepareStatement(query)){
            preparedStatement.setInt(1,task.getId());

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
     * Method to sort tasks by their due date in ascending order.
     * The tasks are retrieved from the database, sorted by due date, and stored in the task list.
     *
     * @return the list of tasks sorted by due date.
     */
    public List<TaskImpl> sortByDueDate() {
        Connection con = DBconnection.getConnection();
        if (con == null) {
            return null; // Return null if the connection fails
        }

        String query = "SELECT id, name, description, dueDate, is_completed FROM tasks ORDER BY dueDate;";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet result = preparedStatement.executeQuery();

            tasks.clear(); // Clear the current list to refresh with sorted tasks

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String description = result.getString("description");
                Date dueDate = result.getDate("dueDate");
                boolean isCompleted = result.getBoolean("is_completed");

                TaskImpl task = new TaskImpl(id, name, description, dueDate.toString());
                task.setCompleted(isCompleted);
                tasks.add(task); // Add each task to the list
            }
        } catch (SQLException mess) {
            mess.printStackTrace(); // Print SQL exception stack trace
        } finally {
            try {
                con.close(); // Close the connection
            } catch (SQLException mess) {
                mess.printStackTrace(); // Print closing connection exception
            }
        }
        return tasks; // Return the sorted list of tasks
    }


    /**
     * Method to search for a task by its ID in the database.
     * @param id the ID of the task to search for.
     * @return the TaskImpl object if found, otherwise null.
     */
    public TaskImpl searchById ( int id)
    {
        Connection con = DBconnection.getConnection();
        if (con == null) {
            return null;
        }
        String query = "SELECT id, name, description, dueDate, is_completed FROM tasks WHERE id= ?;";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                int taskId = result.getInt("id");
                String name = result.getString("name");
                String description = result.getString("description");
                Date dueDate = result.getDate("dueDate");
                boolean isCompleted = result.getBoolean("is_completed");

                TaskImpl task = new TaskImpl(taskId, name, description, dueDate.toString());
                task.setCompleted(isCompleted);
                return task;
            } else {
                System.out.println("Task with ID " + id + "not found.");
                return null;
            }
        } catch (SQLException mess) {
            mess.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException mess) {
                mess.printStackTrace();
            }
        }
        return null;
    }

    // Method to search tasks by name
    public List<TaskImpl> searchByName(String name) {
        List<TaskImpl> tasks = new ArrayList<>();
        String query = "SELECT id, name, description, dueDate, is_completed FROM tasks WHERE name LIKE ?";

        try (Connection connection = DBconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Use a wildcard for partial matching (%)
            preparedStatement.setString(1, "%" + name + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String taskName = resultSet.getString("name");
                String description = resultSet.getString("description");
                String dueDate = resultSet.getString("dueDate");
                boolean isCompleted = resultSet.getBoolean("is_completed");

                TaskImpl task = new TaskImpl(id, taskName, description, dueDate.toString());
                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
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
