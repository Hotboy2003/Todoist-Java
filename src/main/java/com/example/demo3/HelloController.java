package com.example.demo3;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController
{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txt1;

    @FXML
    private Label welcomeText;

    @FXML
    void initialize()
    {
        assert txt1 != null : "fx:id=\"txt1\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert welcomeText != null : "fx:id=\"welcomeText\" was not injected: check your FXML file 'hello-view.fxml'.";
    }
}