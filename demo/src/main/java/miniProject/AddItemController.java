package miniProject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AddItemController {
    @FXML
    private Label AddItemLabel;

    @FXML
    private Button AddItemButton;

    @FXML
    private void handleAddTaskAction() {
        try {
            Main.switchView("/miniProject/Task.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
