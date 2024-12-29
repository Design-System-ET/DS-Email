package com.design_system.ds.email;

import java.io.IOException;
import javafx.fxml.FXML;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class Login {
    
    @FXML
    private TextField node;
    @FXML
    private TextField user_login;
    @FXML
    private TextField password_login;
    @FXML
    private Label msg_login;
    
    @FXML
    private void Register() throws IOException {
        App.setRoot("register");
    }
    
    @FXML
    public void Login(){
        
       //creoun objeto mapper de json
       ObjectMapper mapper = new ObjectMapper();
       
       if(user_login.getText().isEmpty() || node.getText().isEmpty() || password_login.getText().isEmpty()){
           msg_login.setText("Debes completar todos los campos");
           return;
       }
       
       try{
           //leo el archivo json
           File jsonFile = new File("src\\main\\resources\\json\\config.json");
           JsonNode rootNode = mapper.readTree(jsonFile);
           
           //accedo al nodo
           JsonNode connectionNode = rootNode.path(node.getText());
           
           //extraigo los valores para el login
           String user = connectionNode.path("user").asText();
           String password = connectionNode.path("password").asText();
           
           //comparo con las indicadas
           if(user_login.getText().equals(user) && password_login.getText().equals(password)){
               goMain(user_login, node);
           }else{
               
               msg_login.setText("Los datos de acceso no son correctos");

           }
       
       }catch(IOException e){
           e.printStackTrace();
       }
    }
    
    private void goMain(TextField user, TextField node) throws IOException {
        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();

        // Obtener el controlador del formulario cargado
        Main controller = loader.getController();

        // Pasar los valores al controlador
        controller.initializeData(user.getText(), node.getText());

        // Establecer la nueva escena
        Scene scene = new Scene(root);
        Stage stage = (Stage) user.getScene().getWindow(); // Obtener la ventana actual
        stage.setScene(scene);
        stage.show();
    }

    
    public void showMsg() {
        msg_login.setText("");
    }

}
