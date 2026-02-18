package alfred;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;


/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Alfred alfred;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/bat.png"));
    private Image alfredImage = new Image(this.getClass().getResourceAsStream("/images/butler.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        // Show welcome message
        dialogContainer.getChildren().add(
                DialogBox.getAlfredDialog("Good evening Master Wayne! How may I be of service today?", alfredImage)
        );
    }

    /**
     * Injects the Alfred instance.
     */
    public void setAlfred(Alfred a) {
        alfred = a;
    }

    /**
     * Handles user input.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.isEmpty()) {
            return;
        }

        if (input.trim().equals("bye")) {
            // Disable input to prevent further interaction
            userInput.setDisable(true);
            sendButton.setDisable(true);

            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(event -> {
                Platform.exit();
                System.exit(0);
            });
            delay.play();
        }

        String response = alfred.getResponse(input);
        DialogBox alfredDialog;
        if (response.startsWith("CRIKEY!!!")) {
            alfredDialog = DialogBox.getAlfredErrorDialog(response, alfredImage);
        } else {
            alfredDialog = DialogBox.getAlfredDialog(response, alfredImage);
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getAlfredDialog(response, alfredImage)
        );
        userInput.clear();
    }
}