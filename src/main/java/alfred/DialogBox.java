package alfred;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Represents a dialog box in the chat interface.
 * Contains a text message and an avatar image, styled differently
 * for user messages, Alfred responses, and error messages.
 */
public class DialogBox extends HBox {
    @FXML
    private TextFlow dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Creates a new DialogBox with the specified text and avatar image.
     * Loads the FXML layout and initializes the text content and avatar.
     *
     * @param text The message text to display, supports **bold** formatting.
     * @param img  The avatar image to display.
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        buildTextFlow(text);

        setupAvatar(img);
    }

    /**
     * Builds the TextFlow content from the given text.
     * Parses **bold** markdown syntax and applies appropriate styling.
     * Text between ** markers is rendered in bold.
     *
     * @param text The text to parse and display.
     */
    private void buildTextFlow(String text) {
        String[] parts = text.split("\\*\\*");
        for (int i = 0; i < parts.length; i++) {
            Text textNode = new Text(parts[i]);
            textNode.getStyleClass().add("dialog-text");
            // Odd indices are bold (inside ** **)
            if (i % 2 == 1) {
                textNode.setStyle("-fx-font-weight: bold;");
            }
            dialog.getChildren().add(textNode);
        }
    }

    /**
     * Sets up the avatar image with square cropping and circular clipping.
     * Crops the image to a square from the top-center and applies a
     * circular mask to create a round avatar.
     *
     * @param img The image to use as the avatar.
     */
    private void setupAvatar(Image img) {
        displayPicture.setImage(img);

        // Crop image to square (top-center focus)
        double width = img.getWidth();
        double height = img.getHeight();
        double size = Math.min(width, height);
        double x = (width - size) / 2;
        double y = 0;
        displayPicture.setViewport(new Rectangle2D(x, y, size, size));

        // Clip to circle (radius = half of fitWidth/fitHeight)
        double radius = displayPicture.getFitWidth() / 2;
        Circle clip = new Circle(radius, radius, radius);
        displayPicture.setClip(clip);
    }

    /**
     * Flips the dialog box layout so the image appears on the left.
     * Used for Alfred's responses to visually distinguish them from user messages.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a dialog box for user messages.
     * The avatar appears on the right side with user-specific styling.
     *
     * @param text The user's message text.
     * @param img  The user's avatar image.
     * @return A styled DialogBox for the user message.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.dialog.getStyleClass().add("user-label");
        db.displayPicture.getStyleClass().add("user-img");
        return db;
    }

    /**
     * Creates a dialog box for Alfred's responses.
     * The avatar appears on the left side with Alfred-specific styling.
     *
     * @param text Alfred's response text.
     * @param img  Alfred's avatar image.
     * @return A styled DialogBox for Alfred's response.
     */
    public static DialogBox getAlfredDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        db.dialog.getStyleClass().add("alfred-label");
        db.displayPicture.getStyleClass().add("alfred-img");
        return db;
    }

    /**
     * Creates a dialog box for error messages from Alfred.
     * The avatar appears on the left side with error-specific styling.
     *
     * @param text The error message text.
     * @param img  Alfred's avatar image.
     * @return A styled DialogBox for the error message.
     */
    public static DialogBox getAlfredErrorDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        db.dialog.getStyleClass().add("error-label");
        db.displayPicture.getStyleClass().add("error-img");
        return db;
    }
}
