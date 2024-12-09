package miniProject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DisplayTaskController {

    @FXML
    private Button DisplayTaskAddButton;

    @FXML
    private void handleBackToAddItem() {
        try {
            Main.switchView("/miniProject/AddItem.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
