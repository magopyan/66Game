package main;


import javafx.application.Application;
import javafx.stage.Stage;
import utils.FormOpener;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FormOpener.openNewForm("/forms/start.fxml", "66");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
