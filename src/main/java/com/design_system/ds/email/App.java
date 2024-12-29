package com.design_system.ds.email;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.image.Image;


public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"));
        
        Image icon = new Image(getClass().getResourceAsStream("/images/LOGO MODERNO.png"));
        stage.getIcons().add(icon);
    
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("DS Email - © 2004-2028 Design System - Todos los derechos reservados");
        stage.setOpacity(0.85);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static void main(String[] args) {
        launch();
    }

}