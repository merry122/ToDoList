package miniProject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TaskController {

    @FXML
    private TextField TaskNewTask;  // TextField for task name

    @FXML
    private DatePicker TaskDateTask;  // DatePicker for task date

    @FXML
    private TextArea TaskDescription;  // TextArea for task description

    @FXML
    private Button BackToAddItemButton;  // Button to navigate back

    @FXML
    private Button TaskDisplayButton;  // Button to display tasks

    @FXML
    private void handleBackToAddItem() {
        try {
            Main.switchView("/miniProject/AddItem.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDisplayButtonAction() {
        try {
            Main.switchView("/miniProject/DisplayTask.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleTaskAddButton(ActionEvent actionEvent) {
        // Gather input from fields
        String taskName = TaskNewTask.getText();
        String taskDate = (TaskDateTask.getValue() != null) ? TaskDateTask.getValue().toString() : "No date selected";
        String taskDescription = TaskDescription.getText();

        // Debugging output (you can replace this with actual task saving logic)
        System.out.println("Task Added:");
        System.out.println("Name: " + taskName);
        System.out.println("Date: " + taskDate);
        System.out.println("Description: " + taskDescription);

        // Clear the fields after adding the task
        TaskNewTask.clear();
        TaskDateTask.setValue(null);
        TaskDescription.clear();
    }
}
