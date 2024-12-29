package com.design_system.ds.email;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import javafx.scene.control.Label;

public class Register {

    @FXML
    private void Back() throws IOException {
        App.setRoot("login");
    }
    
    @FXML
    private TextField user;
    @FXML
    private TextField password;
    @FXML
    private TextField re_password;
    
    @FXML
    private TextField user_email;
    @FXML
    private TextField password_email;
    
    @FXML
    private TextField connection;
    @FXML
    private TextField host;
    @FXML
    private TextField port;
    @FXML
    private Label msg;
    
    
    @FXML
    public void createConnection() {
        if (user.getText().isEmpty() || password.getText().isEmpty() || re_password.getText().isEmpty() || user_email.getText().isEmpty()
                || password_email.getText().isEmpty() || connection.getText().isEmpty() || host.getText().isEmpty() || port.getText().isEmpty()) {
            System.out.println("Debes completar todos los campos");
            msg.setText("Debes completar todos los campos");
            return;
        }

        if (!password.getText().equals(re_password.getText())) {
            System.out.println("Las passwords no son iguales");
            msg.setText("Las passwords no son iguales");
            return;
        }

        // Crear un ObjectMapper de Jackson
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Leer el archivo JSON existente
            File jsonFile = new File("src\\main\\resources\\json\\config.json");
            ObjectNode rootNode;

            if (jsonFile.exists()) {
                // Si el archivo existe, leer su contenido
                rootNode = (ObjectNode) mapper.readTree(jsonFile);
            } else {
                // Si no existe, crear un nuevo nodo raíz
                rootNode = mapper.createObjectNode();
            }

            // Crear el objeto "connection"
            ObjectNode connectionObject = mapper.createObjectNode();
            connectionObject.put("user", user.getText());
            connectionObject.put("password", password.getText());
            connectionObject.put("re_password", re_password.getText());
            connectionObject.put("user_email", user_email.getText());
            connectionObject.put("password_email", password_email.getText());
            connectionObject.put("host", host.getText());
            connectionObject.put("port", port.getText());

            // Agregar el nuevo nodo al objeto raíz
            rootNode.set(connection.getText(), connectionObject);

            // Escribir el archivo JSON actualizado
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);
            System.out.println("Archivo JSON actualizado correctamente.");
            Back();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}