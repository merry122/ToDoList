package miniProject;
/**
 * TaskListImpl - Implementation of the TaskList abstract class for managing tasks.
 *
 * This class provides the concrete implementation of CRUD operations (Create, Read, Update, Delete)
 * for tasks. It interacts with the database to perform operations on the Task objects.
 *
 * Methods:
 * - `getAll()`: Retrieves all tasks from the database and returns a list of TaskImpl objects.
 * - `addTask(TaskImpl task)`: Adds a new task to the database and returns true if successful.
 * - `deleteTask(int taskId)`: Deletes a task from the database using its ID.
 * - `deleteTask(TaskImpl task)`: Overloaded method to delete a task using a TaskImpl object.
 * - `editTask(TaskImpl task)`: Updates an existing task in the database.
 * - `sortByDueDate()`: Returns a list of tasks sorted by due date.
 * - `searchById(int id)`: Searches for a task by its ID and returns the matching TaskImpl object.
 * - `searchByName(String name)`: Searches for tasks by name and returns a list of matching TaskImpl objects.
 *
 * Author: Krama Raouane (for database handling)
 * Author: Harrouz Meriem (for Java implementation)
 */
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskListImpl extends TaskList {
    private List<TaskImpl> tasks;

    public TaskListImpl() {
        tasks = new ArrayList<>();
    }

    public List<TaskImpl> getAll() {
        Connection con = DBconnection.getConnection();
        if (con == null) {
            System.err.println("Failed to establish database connection.");
            return Collections.emptyList();
        }

        String query = "SELECT id, name, description, dueDate, status, priority, category FROM tasks;";
        List<TaskImpl> fetchedTasks = new ArrayList<>();

        try (PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                LocalDate dueDate = resultSet.getDate("dueDate").toLocalDate();
                String statusString = resultSet.getString("status");
                String priorityString = resultSet.getString("priority");
                String categoryString = resultSet.getString("category");

                TaskImpl.Status status = TaskImpl.Status.valueOf(statusString.toUpperCase());
                TaskImpl.Priority priority = TaskImpl.Priority.valueOf(priorityString.toUpperCase());
                TaskImpl.Category category = TaskImpl.Category.valueOf(categoryString.toUpperCase());

                TaskImpl task = new TaskImpl(id, name, description, dueDate, status, priority, category);
                fetchedTasks.add(task);
            }

            if (fetchedTasks.isEmpty()) {
                System.out.println("No tasks found in the database.");
            } else {
                System.out.println("Fetched " + fetchedTasks.size() + " tasks from the database.");
            }

        } catch (SQLException e) {
            System.err.println("SQL Exception while fetching tasks: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("Failed to close connection: " + e.getMessage());
            }
        }

        tasks = fetchedTasks;
        return tasks;
    }

    public boolean addTask(TaskImpl task) {
        Connection con = DBconnection.getConnection();
        if (con == null) {
            System.err.println("Database connection failed!");
            return false;
        }

        String query = "INSERT INTO tasks (name, description, dueDate, status, priority, category) VALUES (?, ?, ?, ?, ?, ?);";

        try (PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setDate(3, Date.valueOf(task.getDueDate()));
            preparedStatement.setString(4, task.getStatus().name());
            preparedStatement.setString(5, task.getPriority().name());
            preparedStatement.setString(6, task.getCategory().name());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    task.setId(generatedKeys.getInt(1)); // Set the generated ID
                }
                System.out.println("Task added to the database!");
                return true; // Return true if the task was added successfully
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception while adding task: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close(); // Close the connection
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false; // Return false if the task was not added
    }

    @Override
    public void deleteTask(int taskId) {
        Connection con = DBconnection.getConnection();
        if (con == null) {
            System.err.println("Database connection failed!");
            return;
        }

        String query = "DELETE FROM tasks WHERE id = ?;";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, taskId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Task with ID " + taskId + " deleted from the database!");
            } else {
                System.err.println("No task found with ID " + taskId + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close(); // Close the connection
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteTask(TaskImpl task) {
        deleteTask(task.getId()); // Call the deleteTask method with the task's ID
    }

    @Override
    public boolean editTask(TaskImpl task) {
        Connection con = DBconnection.getConnection();
        if (con == null) {
            System.err.println("Database connection failed!");
            return false;
        }

        String query = "UPDATE tasks SET name = ?, description = ?, dueDate = ?, status = ?, priority = ?, category = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setDate(3, Date.valueOf(task.getDueDate()));
            preparedStatement.setString(4, task.getStatus().name());
            preparedStatement.setString(5, task.getPriority().name());
            preparedStatement.setString(6, task.getCategory().name());
            preparedStatement.setInt(7, task.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Task updated successfully!");
                return true; // Return true if the task was updated successfully
            } else {
                System.err.println("No task was updated. Please check the ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close(); // Close the connection
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false; // Return false if the task was not updated
    }

    public List<TaskImpl> sortByDueDate() {
        Connection con = DBconnection.getConnection();
        if (con == null) {
            return Collections.emptyList(); // Return an empty list if the connection fails
        }

        String query = "SELECT id, name, description, dueDate, status, priority, category FROM tasks ORDER BY dueDate;";
        List<TaskImpl> sortedTasks = new ArrayList<>();

        try (PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                LocalDate dueDate = resultSet.getDate("dueDate").toLocalDate();
                String statusString = resultSet.getString("status");
                String priorityString = resultSet.getString("priority");
                String categoryString = resultSet.getString("category");

                TaskImpl.Status status = TaskImpl.Status.valueOf(statusString.toUpperCase());
                TaskImpl.Priority priority = TaskImpl.Priority.valueOf(priorityString.toUpperCase());
                TaskImpl.Category category = TaskImpl.Category.valueOf(categoryString.toUpperCase());

                TaskImpl task = new TaskImpl(id, name, description, dueDate, status, priority, category);
                sortedTasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close(); // Close the connection
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sortedTasks; // Return the sorted list of tasks
    }

    public TaskImpl searchById(int id) {
        Connection con = DBconnection.getConnection();
        if (con == null) {
            return null; // Return null if the connection fails
        }

        String query = "SELECT id, name, description, dueDate, status, priority, category FROM tasks WHERE id = ?;";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int taskId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                LocalDate dueDate = resultSet.getDate("dueDate").toLocalDate();
                String statusString = resultSet.getString("status");
                String priorityString = resultSet.getString("priority");
                String categoryString = resultSet.getString("category");

                TaskImpl.Status status = TaskImpl.Status.valueOf(statusString.toUpperCase());
                TaskImpl.Priority priority = TaskImpl.Priority.valueOf(priorityString.toUpperCase());
                TaskImpl.Category category = TaskImpl.Category.valueOf(categoryString.toUpperCase());
                return new TaskImpl(taskId, name, description, dueDate, status, priority, category);
            } else {
                System.out.println("Task with ID " + id + " not found.");
                return null; // Return null if no task is found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close(); // Close the connection
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null; // Return null if an error occurs
    }

    public List<TaskImpl> searchByName(String name) {
        List<TaskImpl> foundTasks = new ArrayList<>();
        String query = "SELECT id, name, description, dueDate, status, priority, category FROM tasks WHERE name LIKE ?;";

        try (Connection connection = DBconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + name + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String taskName = resultSet.getString("name");
                String description = resultSet.getString("description");
                LocalDate dueDate = resultSet.getDate("dueDate").toLocalDate();
                String statusString = resultSet.getString("status");
                String priorityString = resultSet.getString("priority");
                String categoryString = resultSet.getString("category");

                TaskImpl.Status status = TaskImpl.Status.valueOf(statusString.toUpperCase());
                TaskImpl.Priority priority = TaskImpl.Priority.valueOf(priorityString.toUpperCase());
                TaskImpl.Category category = TaskImpl.Category.valueOf(categoryString.toUpperCase());
                TaskImpl task = new TaskImpl(id, taskName, description, dueDate, status, priority, category);
                foundTasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foundTasks; // Return the list of found tasks
    }
}