module Game66
{
        requires javafx.controls;
        requires javafx.fxml;
        opens main to javafx.fxml;
        exports main;
        opens game to javafx.fxml;
        exports game;
}