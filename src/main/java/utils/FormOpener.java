package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FormOpener {

    public static void openNewForm(String fxmlFileName, String title) { // отваря прозореца на играта

        FXMLLoader fxmlLoad = null;
        try {
            fxmlLoad = new FXMLLoader(new FormOpener().getClass().getResource(fxmlFileName));

            Parent root = fxmlLoad.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.getIcons().add(new Image("/icon.jpg"));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't load new window.");
        }
    }


    public static void openAlert(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText("");
        alert.show();
    }
}
