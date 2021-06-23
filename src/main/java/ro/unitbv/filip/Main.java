package ro.unitbv.filip;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * This is the main class of the application.
 *
 * @author Patrascu Filip
 * @since June 2021
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Login.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/images/Icon.png")));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
